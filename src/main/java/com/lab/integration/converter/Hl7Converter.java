package com.lab.integration.converter;


import java.io.IOException;
import java.util.Map;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.message.ORU_R01;
import ca.uhn.hl7v2.model.v25.segment.*;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.model.DataTypeException;
import com.lab.integration.parser.Hl7Field;
import com.lab.integration.parser.Hl7Fields;
import org.springframework.stereotype.Component;


@Component
public class Hl7Converter {

    public void convert(Map<String, Object> data) {
        try {
            // Create a new HL7 message
            ORU_R01 message = new ORU_R01();
            message.initQuickstart("ORU", "R01", "P");

            // Populate MSH Segment
            populateMshSegment(message.getMSH());

            // Populate PID Segment
            PID pid = message.getPATIENT_RESULT().getPATIENT().getPID();
            populatePidSegment(pid, data);

            // Populate OBR Segment
            OBR obr = message.getPATIENT_RESULT().getORDER_OBSERVATION().getOBR();
            populateObrSegment(obr, data);

            // Populate OBX Segment
            OBX obx = message.getPATIENT_RESULT().getORDER_OBSERVATION().getOBSERVATION().getOBX();
            populateObxSegment(obx, data);

            // Serialize to HL7 String
            PipeParser parser = new PipeParser();
            String hl7Message = parser.encode(message);

            System.out.println("==================HL7===================");
            System.out.println(hl7Message);
            System.out.println("==================END-HL7===================");

        } catch (HL7Exception | IOException e) {
            e.printStackTrace();
        }
    }

    private void populateMshSegment(MSH msh) throws DataTypeException {
        msh.getFieldSeparator().setValue("|");
        msh.getEncodingCharacters().setValue("^~\\&");
        msh.getSendingApplication().getNamespaceID().setValue("LabSystem");
        msh.getSendingFacility().getNamespaceID().setValue("Lab");
        msh.getReceivingApplication().getNamespaceID().setValue("LIS");
        msh.getReceivingFacility().getNamespaceID().setValue("Hospital");
        msh.getDateTimeOfMessage().getTime().setValue("20240404010101");
        msh.getMessageType().getMessageCode().setValue("ORU");
        msh.getMessageType().getTriggerEvent().setValue("R01");
        msh.getMessageControlID().setValue("123456");
        msh.getProcessingID().getProcessingID().setValue("P");
        msh.getVersionID().getVersionID().setValue("2.5");
    }

    private void populatePidSegment(PID pid, Map<String, Object> data) throws DataTypeException {
        Hl7Field patientIdField = Hl7Fields.getField("patientid");
        Hl7Field dateOfBirthField = Hl7Fields.getField("dateofbirth");
        Hl7Field genderField = Hl7Fields.getField("gender");

        pid.getPatientID().getIDNumber().setValue((String) data.get(patientIdField.name()));
        pid.getDateTimeOfBirth().getTime().setValue((String) data.get(dateOfBirthField.name()));
        pid.getAdministrativeSex().setValue((String) data.get(genderField.name()));
    }

    private void populateObrSegment(OBR obr, Map<String, Object> data) throws DataTypeException {
        Hl7Field specimenIdField = Hl7Fields.getField("specimenid");
        Hl7Field orderTimestampField = Hl7Fields.getField("ordertimestamp");

        obr.getFillerOrderNumber().getEntityIdentifier().setValue((String) data.get(specimenIdField.name()));
        obr.getObservationDateTime().getTime().setValue((String) data.get(orderTimestampField.name()));
    }

    private void populateObxSegment(OBX obx, Map<String, Object> data) throws HL7Exception {
        Hl7Field testTypeField = Hl7Fields.getField("testtype");
        Hl7Field valueField = Hl7Fields.getField("value");
        Hl7Field unitsField = Hl7Fields.getField("units");
        Hl7Field statusField = Hl7Fields.getField("status");

        obx.getObservationIdentifier().getIdentifier().setValue((String) data.get(testTypeField.name()));
        obx.getObservationValue(0).getData().parse((String) data.get(valueField.name()));
        obx.getUnits().getText().setValue((String) data.get(unitsField.name()));
        obx.getObservationResultStatus().setValue((String) data.get(statusField.name()));
    }
}