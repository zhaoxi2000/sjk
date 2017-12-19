/**
 * 
 */
package com.ijinshan.sjk.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-26 下午5:21:30
 * </pre>
 */
public class PhoneMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(PhoneMapperTest.class);

    @Resource(name = "phoneInfoMapper")
    private PhoneInfoMapper phoneMapper;

    @Test
    public void testPhoneBrandList() {
        List<String> brandList = phoneMapper.getAllPhoneBrand();
        for (String string : brandList) {
            logger.debug(string);
        }
    }
}
