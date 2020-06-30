package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TransactionMapper;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.repository.model.extend.AppointmentBriefInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.AppointmentService;
import com.wequan.bu.service.StripeService;
import com.wequan.bu.util.AppointmentStatus;
import com.wequan.bu.util.TransactionStatus;
import org.apache.ibatis.session.RowBounds;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ChrisChen
 */
@Service
public class AppointmentServiceImpl extends AbstractService<Appointment> implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private StripeService stripeService;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(appointmentMapper);
    }

    @Override
    public List<Appointment> findByTutorId(Integer tutorId) {
        return appointmentMapper.selectByTutorId(tutorId);
    }

    @Override
    public List<AppointmentBriefInfo> getUserAppointments(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return appointmentMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    public void makeAppointment(Appointment appointment) {
        appointment.setCreateTime(LocalDateTime.now());
        appointment.setStatus((short) 0);
        appointment.setFee((int)(long) calculateFee(appointment));
        appointmentMapper.insertSelective(appointment);
    }

    @Override
    public void updateTransactionIdByPrimaryKey(Integer appointmentId, String transactionId) {
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setTransactionId(transactionId);
        appointmentMapper.updateByPrimaryKeySelective(appointment);
    }


    @Override
    public void updateAppointmentAndGenerateNewTransaction(Appointment appointment) throws Exception{
        Appointment oldRecord = appointmentMapper.selectByPrimaryKey(appointment.getId());
        if(oldRecord == null) {
            throw new Exception("no such appointment");
        }

        Transaction transaction = transactionMapper.selectByPrimaryKey(oldRecord.getTransactionId());
        if(transaction.getStatus().equals(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue())){
            /**delete old payment intent (third_party_transaction_id)
             * delete old transaction info in table (transaction_id)
             * handled by webhook payment_intent.canceled event
             */
            System.out.println("============================== enter if statement");
            stripeService.cancelPaymentIntent(transaction.getThirdPartyTransactionId());
            //calculate fee
            appointment.setFee((int)(long) calculateFee(oldRecord));
            appointment.setUpdateTime(LocalDateTime.now());
            appointment.setTransactionId("-1");
            //update appointment
            appointmentMapper.updateByPrimaryKeySelective(appointment);
            //generate new payment intent (appointment_id, type)
            stripeService.createPaymentIntent(appointment.getId());
        }else{
            throw new Exception("can't update the appointment after customer has paid money");
        }
    }

    @Override
    public void updateStatus(String paymentIntentId, AppointmentStatus status) {
        Transaction transaction = transactionMapper.selectByThirdPartyTransactionId(paymentIntentId);

        Appointment appointment = new Appointment();
        appointment.setStatus(status.getValue());
        appointment.setTransactionId(transaction.getId());
        appointmentMapper.updateByTransactionIdSelective(appointment);
    }

    /**
     * calculate the total fee of an appointment
     * @param appointment
     * @return the total fee in cents
     */
    private Long calculateFee(Appointment appointment){
        Tutor tutor = tutorMapper.selectByPrimaryKey(appointment.getTutorId());
        LocalDateTime start = appointment.getStartTime();
        LocalDateTime end = appointment.getEndTime();
        Duration duration = Duration.between(start, end);
        Long minues = duration.toMinutes();
        Long hours = duration.toHours();
        //round up to nearest integer
        if(minues % 60 != 0){
            hours++;
        }
        return hours * tutor.getHourlyRate();
    }
}
