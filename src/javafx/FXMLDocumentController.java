
package javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;

public class FXMLDocumentController {
    @FXML
    private TableView<Livro> tableView;
    @FXML
    private TableColumn<Livro, String> titleColumn;
    @FXML
    private TableColumn<Livro, String> authorColumn;
    @FXML
    private TableColumn<Livro, Long> publishmentYearColumn;
    @FXML
    private TableColumn<Livro, String> publisherColumn;
    @FXML
    private TableColumn<Livro, Long> quantityColumn;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField publishmentYearField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField quantityField;
    @FXML
    private ProgressIndicator progressIndicator;

    private ObservableList<Livro> livros;
    
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private Consumer<? super HttpResponse<String>> response;

    public void initialize() {
        livros = FXCollections.observableArrayList();
        tableView.setItems(livros);

        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        publishmentYearColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getPublishmentYear()).asObject());
        publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getQuantity()).asObject());
        searchBooks();
    }
    
    private void validateLongValue(String value, String erro) throws ParseException {
        try {
            Long.parseLong(value);
        } catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Validação");
            alert.setHeaderText(null);
            alert.setContentText(erro);
            alert.showAndWait();
            throw new ParseException(erro, 0);
        }
    }

    @FXML
    private void adicionarLivro() throws ParseException {
        validateLongValue(publishmentYearField.getText(), "Campo Ano de Publicação deve ser um valor inteiro");
        validateLongValue(quantityField.getText(), "Campo Quantidade deve ser um valor inteiro");
        
        String title = titleField.getText();
        String author = authorField.getText();
        Long publishmentYear = Long.parseLong(publishmentYearField.getText());
        String publisher = publisherField.getText();
        Long quantity = Long.parseLong(quantityField.getText());

        Livro livro = new Livro(title, author, publishmentYear, publisher, quantity);
        livros.add(livro);
        sendBook(livro);

        limparCampos();
    }

    @FXML
    private void editarLivro() {
        Livro livroSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            livroSelecionado.setTitle(titleField.getText());
            livroSelecionado.setAuthor(authorField.getText());
            livroSelecionado.setPublishmentYear(Long.parseLong(publishmentYearField.getText()));
            livroSelecionado.setPublisher(publisherField.getText());
            livroSelecionado.setQuantity(Long.parseLong(quantityField.getText()));
            tableView.refresh();
            limparCampos();
        }
    }

    @FXML
    private void removerLivro() {
        Livro livroSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            livros.remove(livroSelecionado);
        }
    }

    private void limparCampos() {
        titleField.clear();
        authorField.clear();
        publishmentYearField.clear();
        publisherField.clear();
        quantityField.clear();
    }
    
    private void searchBooks() {
        String url = "https://classroommission.onrender.com/storage-management/book/search-all";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        progressIndicator.setOpacity(1);
        progressIndicator.setProgress(0.5);
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::statusCode)
            .thenAccept(statusCode -> {
                progressIndicator.setProgress(0.7);
                if (statusCode == 200) {
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenAccept(responseBody -> {
                            progressIndicator.setProgress(0.8);
                            parseBooks(responseBody);
                        })
                        .exceptionally(e -> {
                            System.err.println("Erro ao processar o corpo da resposta: " + e.getMessage());
                            return null;
                        });
                } else {
                    System.err.println("Erro ao buscar os livros: " + statusCode);
                }
            })
            .exceptionally(e -> {
                System.err.println("Erro ao realizar requisição assíncrona: " + e.getMessage());
                return null;
            });
    }

    private void parseBooks(String jsonResponse) {
        try {
            JSONArray booksArray = new JSONArray(jsonResponse);
            livros.clear();
            progressIndicator.setProgress(0.9);
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject bookJson = booksArray.getJSONObject(i);

                String title = bookJson.getString("title");
                String author = bookJson.getString("author");
                Long publishmentYear = bookJson.getLong("publishmentYear");
                String publisher = bookJson.getString("publisher");
                Long quantity = bookJson.getLong("quantity");

                Livro livro = new Livro(title, author, publishmentYear, publisher, quantity);
                livros.add(livro);
            }
            progressIndicator.setProgress(1);
            progressIndicator.setOpacity(0);
            progressIndicator.setProgress(0);

        } catch (Exception e) {
            System.err.println("Erro ao parsear a resposta JSON: " + e.getMessage());
        }
    }
    
    private void sendBook(Livro livro) {
        String url = "https://classroommission.onrender.com/storage-management/book";
        JSONObject jsonBook = new JSONObject();

        jsonBook.put("title", livro.getTitle());
        jsonBook.put("author", livro.getAuthor());
        jsonBook.put("publishmentYear", livro.getPublishmentYear());
        jsonBook.put("publisher", livro.getPublisher());
        jsonBook.put("quantity", livro.getQuantity());

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBook.toString()))
            .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::statusCode)
            .thenAccept(statusCode -> {
                if (statusCode == 201) {
                    System.out.println("Livro adicionado com sucesso!");
                } else {
                    System.err.println("Erro ao adicionar o livro. Código de status: " + statusCode);
                }
            })
            .exceptionally(e -> {
                System.err.println("Erro ao realizar requisição para adicionar o livro: " + e.getMessage());
                return null;
            });
    }


}

