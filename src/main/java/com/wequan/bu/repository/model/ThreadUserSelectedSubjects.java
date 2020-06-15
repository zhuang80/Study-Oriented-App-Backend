package com.wequan.bu.repository.model;

import lombok.Data;

/**
 * Database Table Remarks:
 *   用于保存用户选择的科目列表（一对多）
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table bu.wq_thread_user_selected_subjects
 *
 * @mbg.generated do_not_delete_during_merge
 */
@Data
public class ThreadUserSelectedSubjects {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_thread_user_selected_subjects.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   科目id，以逗号分隔
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_thread_user_selected_subjects.subject_ids
     *
     * @mbg.generated
     */
    private String subjectIds;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread_user_selected_subjects
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ThreadUserSelectedSubjects other = (ThreadUserSelectedSubjects) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSubjectIds() == null ? other.getSubjectIds() == null : this.getSubjectIds().equals(other.getSubjectIds()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread_user_selected_subjects
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSubjectIds() == null) ? 0 : getSubjectIds().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread_user_selected_subjects
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", subjectIds=").append(subjectIds);
        sb.append("]");
        return sb.toString();
    }
}