package com.kivi.framework.web.jwt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.util.kit.CollectionKit;

public class JwtUserKitTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAudience() {
        final String identifier = "13800100500";
        JwtUserKit jwtUser = JwtUserKit.builder().id(1L).identifier(identifier).build();
        String[] lists = jwtUser.audience();

        JwtUserDTO user = JwtUserKit.audience(CollectionKit.newArrayList(lists));

        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals(identifier, user.getIdentifier());
    }

}
