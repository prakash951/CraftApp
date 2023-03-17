package com.prakash.app.util;

import com.prakash.app.dto.response.Response;
import java.util.List;

public class BuildResponse {

  public static Response buildResponse(int status, Object response, List<String> errors) {
    Response response1 =
        Response.builder().status(status).response(response).errors(errors).build();
    return response1;
  }
}
