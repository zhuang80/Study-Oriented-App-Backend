package com.wequan.bu.util;

public class FileTool {
    public static enum Type {
        pdf, docx
    }

    public static String addPDFSuffix(String name) {
        return name + "." + Type.pdf;
    }

    public static String addDOCXSuffix(String name) {
        return name + "." + Type.docx;
    }
}
