package com.lab.integration.parser;

import java.util.ArrayList;
import java.util.List;

public class Hl7Fields {
    private static List<Hl7Field> FIELDS = List.of(new Hl7Field("patientid" , "PID" , 3),
            new Hl7Field("dateofbirth" , "PID" , 7),
            new Hl7Field("gender" , "PID" , 8),
            new Hl7Field("specimenid" , "OBR" , 2),
            new Hl7Field("ordertimestamp" , "OBR" , 7),
            new Hl7Field("testtype" , "OBX" , 3),
            new Hl7Field("value" , "OBX" , 5),
            new Hl7Field("units" , "OBX" , 6),
            new Hl7Field("status" , "OBX" , 10)
    ) ;

    public static Hl7Field getField(String fieldName) {
        return FIELDS.stream().filter(f -> f.name().equals(fieldName)).findFirst().get();
    }
}
