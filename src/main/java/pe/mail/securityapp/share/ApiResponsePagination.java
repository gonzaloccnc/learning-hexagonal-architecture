package pe.mail.securityapp.share;

import lombok.experimental.SuperBuilder;
import java.util.Map;

@SuperBuilder
public abstract class ApiResponsePagination<T> {
  protected String message;
  protected int status;
  protected Map<String, String> errors;
  protected T data;
  protected int page;
  protected int hints;
  protected int totalPages;
  protected String nextPage;
  protected String prevPage;
  protected String url;
}
