package ksnu.sw.uilab.annong;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ksnu.sw.uilab.annong.utils.validator.DataTypeValidator;
import ksnu.sw.uilab.annong.utils.validator.exception.NotNullDataTypeException;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {
    @Test
    public void validateRequireDate() {
        try{
            String data1 = DataTypeValidator.validateNotNullTypeData("없음", true);
            String data2 = DataTypeValidator.validateNotNullTypeData("23.0", true);
            Assert.assertEquals(data1, " ");
            Assert.assertEquals(data2, "23.0");
        }catch (NotNullDataTypeException e){

        }
    }
}
