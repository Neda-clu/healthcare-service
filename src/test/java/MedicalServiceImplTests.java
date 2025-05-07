import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;

public class MedicalServiceImplTests {
    @Test
    void checkBloodPressure_test() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        BloodPressure bloodPressure = new BloodPressure(120, 80);
        medicalService.checkBloodPressure("3", bloodPressure);
        Mockito.verify(alertService, Mockito.times(1)).send(anyString());
    }

    @Test
    void checkTemperature_test() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        BigDecimal bigDecimal = new BigDecimal("36.6");
        medicalService.checkTemperature("3", bigDecimal);
        Mockito.verify(alertService, Mockito.times(1)).send(anyString());
    }

    @Test
    void check_ok() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(anyString()).getHealthInfo().getBloodPressure())
                .thenReturn(new BloodPressure(120, 80));
        Mockito.when(patientInfoRepository.getById(anyString()).getHealthInfo().getNormalTemperature())
                .thenReturn(new BigDecimal("36.6"));
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        Mockito.verify(sendAlertService, Mockito.isNull()).send("");
        
    }
}
