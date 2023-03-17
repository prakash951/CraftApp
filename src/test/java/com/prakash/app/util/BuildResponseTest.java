package com.prakash.app.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.prakash.app.dto.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BuildResponseTest {

  @Test
  void testStatus() {
    Response myResponse = BuildResponse.buildResponse(0, null, null);
    assertEquals(0, myResponse.getStatus());
    assertEquals(null, myResponse.getResponse());
    assertEquals(null, myResponse.getErrors());
  }

  @Test
  void testErrors() {
    List<String> errors = new ArrayList<>();
    errors.add("Error");
    Response myResponse = BuildResponse.buildResponse(-1, null, errors);
    assertEquals(-1, myResponse.getStatus());
    assertEquals(null, myResponse.getResponse());
    assertEquals(errors, myResponse.getErrors());
  }
}
