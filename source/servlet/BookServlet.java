package servlet;

import domain.Book;
import domain.User;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.UUID;

import persistence.BookDAO;
import persistence.UserDAO;

import shared.Json;

@SuppressWarnings({"serial"})
@WebServlet("/books")
public final class BookServlet extends HttpServlet {
  private ServletService service = new ServletService();
  private BookDAO persistence = new BookDAO();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      service.dispatch(response, new Payload<>(HttpStatus.OK, persistence.fetch()));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Json json = Json.from(request.getReader());

      if (service.isAuthorized(request)) {
        throw new IllegalArgumentException("Invalid token");
      }

      UUID userID = UUID.fromString(json.get("id").getAsString());

      String publisher = json.get("publisher").getAsString();

      String name = json.get("name").getAsString();

      double coverPrice = json.get("coverPrice").getAsDouble();

      int pageCount = json.get("pageCount").getAsInt();

      String dateString = json.get("publishedAt").getAsString();

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      LocalDate publishedAt = LocalDate.parse(dateString, formatter);

      String description = json.get("description").getAsString();

      User user = new UserDAO().fetch(userID).orElse(null);

      Book book = new Book();

      book.setUser(user);

      book.setPublisher(publisher);

      book.setName(name);

      book.setCoverPrice(coverPrice);

      book.setPageCount(pageCount);

      book.setPublishedAt(publishedAt);

      if (!persistence.save(book)) {
        throw new IllegalArgumentException(
            "Book '"
                + book.getName()
                + "' already belongs to '"
                + user.getUsername()
                + "' collection");
      }

      service.dispatch(response, new Payload<>(HttpStatus.CREATED, book));
    } catch (IllegalArgumentException exception) {
      service.dispatch(response, new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage()));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) {}

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) {}
}
