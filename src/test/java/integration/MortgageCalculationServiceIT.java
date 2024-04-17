package integration;

import com.company.UI.CreateInputData;
import com.company.configuration.CalculatorConfiguration;
import com.company.service.InputDataRepository;
import com.company.service.InstallmentCalculationService;
import com.company.service.MortgageCalculationService;
import fixtures.TestDataFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringJUnitConfig(classes = {CalculatorConfiguration.class})
public class MortgageCalculationServiceIT {

   private static final Path RESULT_FILE_PATH = Paths.get("build/generated/calculationResult.txt");

   @Autowired
   private MortgageCalculationService mortgageCalculationService;

   @Autowired
   private CreateInputData createInputData;

   @Mock
   private InputDataRepository inputDataRepository;

   @BeforeEach
   public void setUp() {
      Assertions.assertNotNull(mortgageCalculationService);
      Assertions.assertNotNull(createInputData);
   }

   @Test
   @DisplayName("That whole app calculation works correctly")
      void test(){
      //given, when
      createInputData.readDataFromFile(inputDataRepository);
   }
}
