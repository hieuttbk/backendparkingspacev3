package com.sparking.helper;

public class HandleSlotID {
    public static int handleSlotId(Integer fieldId, Integer slotId) {
        return fieldId * 1000 + slotId;
    }
}