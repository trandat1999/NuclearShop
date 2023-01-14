package com.tranhuudat.nuclearshop.type;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
public enum OrderImportStatus {
    NEW,PAID,FINISHED;
    public static class Constants {
        public static final String NEW = "NEW";
        public static final String PAID = "PAID";
        public static final String FINISHED = "FINISHED";
    }
}
