
package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLDocumentController {
    @FXML
    private TableView<Livro> tableView;
    @FXML
    private TableColumn<Livro, String> titleColumn;
    @FXML
    private TableColumn<Livro, String> authorColumn;
    @FXML
    private TableColumn<Livro, Integer> publishmentYearColumn;
    @FXML
    private TableColumn<Livro, String> publisherColumn;
    @FXML
    private TableColumn<Livro, Integer> quantityColumn;

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

    private ObservableList<Livro> livros;

    public void initialize() {
        livros = FXCollections.observableArrayList();
        tableView.setItems(livros);

        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        publishmentYearColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPublishmentYear()).asObject());
        publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
    }

    @FXML
    private void adicionarLivro() {
        String title = titleField.getText();
        String author = authorField.getText();
        int publishmentYear = Integer.parseInt(publishmentYearField.getText());
        String publisher = publisherField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        Livro livro = new Livro(title, author, publishmentYear, publisher, quantity);
        livros.add(livro);

        limparCampos();
    }

    @FXML
    private void editarLivro() {
        Livro livroSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            livroSelecionado.setTitle(titleField.getText());
            livroSelecionado.setAuthor(authorField.getText());
            livroSelecionado.setPublishmentYear(Integer.parseInt(publishmentYearField.getText()));
            livroSelecionado.setPublisher(publisherField.getText());
            livroSelecionado.setQuantity(Integer.parseInt(quantityField.getText()));
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
}

