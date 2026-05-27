package com.phungloccoffee.gui.controller.pos;

import com.phungloccoffee.gui.model.OrderItem;
import com.phungloccoffee.gui.model.ProductOption;
import com.phungloccoffee.gui.model.ProductOption.ProductStatus;
import com.phungloccoffee.gui.model.ToppingItem;
import com.phungloccoffee.util.CurrencyFormatter;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class POSMainController {
    private static final String CATEGORY_ALL = "Tất cả";
    private static final List<String> CATEGORIES = List.of(CATEGORY_ALL, "Cà phê", "Trà sữa", "Trà", "Bánh", "Topping");
    private static final List<String> SUGAR_LEVELS = List.of("0%", "30%", "50%", "70%", "100%");
    private static final List<String> ICE_LEVELS = List.of("Không đá", "Ít đá", "Bình thường", "Nhiều đá");

    @FXML private TextField searchProductField;
    @FXML private HBox categoryChipBox;
    @FXML private TilePane productTilePane;
    @FXML private VBox cartItemsBox;
    @FXML private Label totalLabel;
    @FXML private Label notificationLabel;
    @FXML private StackPane customizeOverlay;
    @FXML private StackPane customizeDialogContainer;

    private final List<ProductOption> products = new ArrayList<>();
    private final List<ProductOption> toppingCatalog = new ArrayList<>();
    private final List<OrderItem> cartItems = new ArrayList<>();
    private final List<CheckBox> toppingCheckboxes = new ArrayList<>();

    private String selectedCategory = CATEGORY_ALL;
    private ProductOption currentProduct;
    private OrderItem editingItem;
    private int currentQuantity = 1;
    private Label quantityValueLabel;
    private ToggleGroup sugarToggleGroup;
    private ToggleGroup iceToggleGroup;
    private TextArea noteTextArea;
    private PauseTransition notificationDelay;

    @FXML
    private void initialize() {
        initProductData();
        initCategoryChips();
        searchProductField.textProperty().addListener((observable, oldValue, newValue) -> filterProducts());
        filterProducts();
        renderCartItems();
    }

    @FXML
    private void addSelectedProduct() {
        // Kept for backward compatibility with older FXML actions.
    }

    @FXML
    private void removeSelectedProduct() {
        if (!cartItems.isEmpty()) {
            handleRemoveCartItem(cartItems.get(cartItems.size() - 1));
        }
    }

    @FXML
    private void createOrder() {
        if (cartItems.isEmpty()) {
            showInlineMessage("Vui lòng thêm món trước khi tạo hóa đơn.");
            return;
        }
        showInlineMessage("Đã chuẩn bị chi tiết hóa đơn với đầy đủ tùy chỉnh món.");
    }

    private void initProductData() {
        products.clear();
        products.addAll(List.of(
                product("CF001", "Cà phê sữa", "Cà phê", 35000, ProductStatus.AVAILABLE),
                product("CF002", "Bạc xỉu", "Cà phê", 38000, ProductStatus.AVAILABLE),
                product("CF003", "Americano", "Cà phê", 32000, ProductStatus.AVAILABLE),
                product("CF004", "Cold Brew", "Cà phê", 45000, ProductStatus.OUT_OF_STOCK),

                product("TS001", "Trà sữa trân châu", "Trà sữa", 42000, ProductStatus.AVAILABLE),
                product("TS002", "Trà sữa matcha", "Trà sữa", 45000, ProductStatus.AVAILABLE),
                product("TS003", "Trà sữa socola", "Trà sữa", 45000, ProductStatus.PAUSED),

                product("TR001", "Trà đào cam sả", "Trà", 39000, ProductStatus.AVAILABLE),
                product("TR002", "Trà vải", "Trà", 39000, ProductStatus.AVAILABLE),
                product("TR003", "Trà lài mật ong", "Trà", 36000, ProductStatus.AVAILABLE),

                product("BK001", "Bánh tiramisu", "Bánh", 55000, ProductStatus.AVAILABLE),
                product("BK002", "Croissant bơ", "Bánh", 32000, ProductStatus.AVAILABLE),
                product("BK003", "Bánh phô mai", "Bánh", 49000, ProductStatus.OUT_OF_STOCK),

                product("TP001", "Trân châu đen", "Topping", 10000, ProductStatus.AVAILABLE),
                product("TP002", "Trân châu trắng", "Topping", 12000, ProductStatus.AVAILABLE),
                product("TP003", "Pudding trứng", "Topping", 15000, ProductStatus.AVAILABLE),
                product("TP004", "Kem cheese", "Topping", 15000, ProductStatus.AVAILABLE)
        ));

        toppingCatalog.clear();
        toppingCatalog.addAll(products.stream()
                .filter(ProductOption::isToppingCategory)
                .toList());
    }

    private ProductOption product(String id, String name, String category, int price, ProductStatus status) {
        return new ProductOption(id, name, category, BigDecimal.valueOf(price), status);
    }

    private void initCategoryChips() {
        categoryChipBox.getChildren().clear();
        for (String category : CATEGORIES) {
            Button chip = new Button(category);
            chip.getStyleClass().add("product-category-chip");
            chip.setOnAction(event -> {
                selectedCategory = category;
                updateCategoryChipStyles();
                filterProducts();
            });
            categoryChipBox.getChildren().add(chip);
        }
        updateCategoryChipStyles();
    }

    private void updateCategoryChipStyles() {
        for (Node node : categoryChipBox.getChildren()) {
            if (node instanceof Button button) {
                button.getStyleClass().remove("product-category-chip-active");
                if (Objects.equals(button.getText(), selectedCategory)) {
                    addStyleClass(button, "product-category-chip-active");
                }
            }
        }
    }

    private void filterProducts() {
        String keyword = normalize(searchProductField.getText());
        List<ProductOption> filteredProducts = products.stream()
                .filter(product -> CATEGORY_ALL.equals(selectedCategory) || selectedCategory.equals(product.getCategory()))
                .filter(product -> keyword.isBlank()
                        || normalize(product.getProductName()).contains(keyword)
                        || normalize(product.getProductId()).contains(keyword))
                .toList();
        renderProductCards(filteredProducts);
    }

    private void renderProductCards(List<ProductOption> filteredProducts) {
        if (filteredProducts.isEmpty()) {
            Label emptyState = new Label("Không tìm thấy món phù hợp");
            emptyState.getStyleClass().add("cart-item-detail");
            productTilePane.getChildren().setAll(emptyState);
            return;
        }
        productTilePane.getChildren().setAll(filteredProducts.stream()
                .map(this::createProductCard)
                .toList());
    }

    private VBox createProductCard(ProductOption product) {
        Label categoryBadge = new Label(product.getCategory());
        categoryBadge.getStyleClass().add("product-card-category");

        Label statusBadge = createStatusBadge(product.getStatus());
        Region badgeSpacer = new Region();
        HBox.setHgrow(badgeSpacer, Priority.ALWAYS);
        HBox topRow = new HBox(8, categoryBadge, badgeSpacer, statusBadge);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label name = new Label(product.getProductName());
        name.getStyleClass().add("product-card-name");
        name.setWrapText(true);

        Label price = new Label(formatMoneyCompact(product.getBasePrice()));
        price.getStyleClass().add("product-card-price");

        Button addButton = new Button(product.isToppingCategory() ? "Chọn kèm món" : "Thêm");
        addButton.getStyleClass().add("primary-button");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setOnAction(event -> {
            if (product.isToppingCategory()) {
                showInlineMessage("Topping được chọn khi tùy chỉnh món uống.");
                return;
            }
            openCustomizeProductDialog(product);
        });

        if (!product.getStatus().isAvailable()) {
            addButton.setDisable(true);
            addButton.setText(product.getStatus() == ProductStatus.OUT_OF_STOCK ? "Hết hàng" : "Tạm ngưng");
        }

        VBox card = new VBox(12, topRow, name, price, addButton);
        card.getStyleClass().add("product-card");
        if (!product.getStatus().isAvailable()) {
            addStyleClass(card, "product-card-disabled");
        }
        card.setPrefWidth(225);
        card.setMinHeight(172);
        return card;
    }

    private Label createStatusBadge(ProductStatus status) {
        Label badge = new Label(status.getLabel());
        badge.getStyleClass().add(switch (status) {
            case AVAILABLE -> "product-status-available";
            case OUT_OF_STOCK -> "product-status-out";
            case PAUSED -> "product-status-paused";
        });
        return badge;
    }

    private void openCustomizeProductDialog(ProductOption product) {
        if (!product.getStatus().isAvailable()) {
            return;
        }
        currentProduct = product;
        editingItem = null;
        currentQuantity = 1;
        renderCustomizeDialog(product, null);
        customizeOverlay.setManaged(true);
        customizeOverlay.setVisible(true);
    }

    private void closeCustomizeProductDialog() {
        customizeDialogContainer.getChildren().clear();
        customizeOverlay.setVisible(false);
        customizeOverlay.setManaged(false);
        currentProduct = null;
        editingItem = null;
        toppingCheckboxes.clear();
    }

    private void renderCustomizeDialog(ProductOption product, OrderItem existingItem) {
        toppingCheckboxes.clear();
        sugarToggleGroup = null;
        iceToggleGroup = null;
        noteTextArea = null;

        VBox headerText = new VBox(5);
        Label dialogTitle = new Label(product.getProductName());
        dialogTitle.getStyleClass().add("order-option-dialog-title");

        Label basePrice = new Label("Giá gốc: " + formatMoneyCompact(product.getBasePrice()));
        basePrice.getStyleClass().add("order-option-meta");

        HBox metadata = new HBox(8, createCategoryBadge(product.getCategory()), basePrice, createStatusBadge(product.getStatus()));
        metadata.setAlignment(Pos.CENTER_LEFT);
        headerText.getChildren().addAll(dialogTitle, metadata);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        Button closeButton = new Button("Đóng");
        closeButton.getStyleClass().add("cart-action-button");
        closeButton.setOnAction(event -> closeCustomizeProductDialog());

        HBox header = new HBox(12, headerText, headerSpacer, closeButton);
        header.setAlignment(Pos.TOP_LEFT);
        header.getStyleClass().add("order-option-dialog-header");

        VBox body = new VBox(12);
        body.getChildren().add(createQuantitySection());
        if (product.isDrink()) {
            sugarToggleGroup = new ToggleGroup();
            iceToggleGroup = new ToggleGroup();
            body.getChildren().add(createToggleOptionSection("Mức đường", SUGAR_LEVELS, sugarToggleGroup, existingItem == null ? "100%" : existingItem.getSugarLevel()));
            body.getChildren().add(createToggleOptionSection("Mức đá", ICE_LEVELS, iceToggleGroup, existingItem == null ? "Bình thường" : existingItem.getIceLevel()));
            body.getChildren().add(createToppingSection(existingItem));
        }
        body.getChildren().add(createNoteSection(existingItem));

        ScrollPane bodyScroll = new ScrollPane(body);
        bodyScroll.setFitToWidth(true);
        bodyScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bodyScroll.setMaxHeight(500);
        bodyScroll.getStyleClass().add("order-option-scroll");

        Button cancelButton = new Button("Hủy");
        cancelButton.getStyleClass().add("secondary-button");
        cancelButton.setOnAction(event -> closeCustomizeProductDialog());

        Button submitButton = new Button(existingItem == null ? "Thêm vào giỏ" : "Cập nhật món");
        submitButton.getStyleClass().add("primary-button");
        submitButton.setOnAction(event -> handleAddCustomizedItem());

        Region footerSpacer = new Region();
        HBox.setHgrow(footerSpacer, Priority.ALWAYS);
        HBox footer = new HBox(10, footerSpacer, cancelButton, submitButton);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.getStyleClass().add("order-option-dialog-footer");

        VBox dialog = new VBox(16, header, bodyScroll, footer);
        dialog.getStyleClass().add("order-option-dialog");
        dialog.setMaxWidth(580);
        dialog.setPrefWidth(560);
        customizeDialogContainer.getChildren().setAll(dialog);
    }

    private Label createCategoryBadge(String category) {
        Label badge = new Label(category);
        badge.getStyleClass().add("product-card-category");
        return badge;
    }

    private VBox createQuantitySection() {
        Label title = new Label("Số lượng");
        title.getStyleClass().add("order-option-section-title");

        Button minusButton = new Button("-");
        minusButton.getStyleClass().add("quantity-button");
        minusButton.setOnAction(event -> {
            if (currentQuantity > 1) {
                currentQuantity--;
                updateQuantityLabel();
            }
        });

        quantityValueLabel = new Label(String.valueOf(currentQuantity));
        quantityValueLabel.getStyleClass().add("quantity-value");

        Button plusButton = new Button("+");
        plusButton.getStyleClass().add("quantity-button");
        plusButton.setOnAction(event -> {
            currentQuantity++;
            updateQuantityLabel();
        });

        HBox quantityControls = new HBox(8, minusButton, quantityValueLabel, plusButton);
        quantityControls.setAlignment(Pos.CENTER_LEFT);
        return createSection(title, quantityControls);
    }

    private VBox createToggleOptionSection(String titleText, List<String> options, ToggleGroup toggleGroup, String selectedValue) {
        Label title = new Label(titleText);
        title.getStyleClass().add("order-option-section-title");

        HBox optionRow = new HBox(8);
        optionRow.setAlignment(Pos.CENTER_LEFT);
        for (String option : options) {
            ToggleButton toggleButton = new ToggleButton(option);
            toggleButton.getStyleClass().add("option-chip");
            toggleButton.setUserData(option);
            toggleButton.setToggleGroup(toggleGroup);
            optionRow.getChildren().add(toggleButton);
            if (Objects.equals(option, selectedValue)) {
                toggleButton.setSelected(true);
            }
        }

        if (toggleGroup.getSelectedToggle() == null && !toggleGroup.getToggles().isEmpty()) {
            toggleGroup.getToggles().get(0).setSelected(true);
        }
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> updateOptionChipStyles(toggleGroup));
        updateOptionChipStyles(toggleGroup);

        return createSection(title, optionRow);
    }

    private VBox createToppingSection(OrderItem existingItem) {
        Label title = new Label("Topping");
        title.getStyleClass().add("order-option-section-title");

        VBox toppingList = new VBox(8);
        for (ProductOption topping : toppingCatalog) {
            CheckBox checkBox = new CheckBox(topping.getProductName() + " +" + formatMoneyCompact(topping.getBasePrice()));
            checkBox.getStyleClass().add("topping-checkbox");
            checkBox.setUserData(topping);
            checkBox.setSelected(existingItem != null && hasTopping(existingItem, topping.getProductId()));
            if (!topping.getStatus().isAvailable()) {
                checkBox.setText(checkBox.getText() + " - Hết nguyên liệu");
                checkBox.setDisable(true);
            }
            toppingCheckboxes.add(checkBox);
            toppingList.getChildren().add(checkBox);
        }
        return createSection(title, toppingList);
    }

    private VBox createNoteSection(OrderItem existingItem) {
        Label title = new Label("Ghi chú");
        title.getStyleClass().add("order-option-section-title");

        noteTextArea = new TextArea();
        noteTextArea.setPromptText("Ví dụ: ít ngọt, không lấy đá, thêm sữa, tách riêng topping...");
        noteTextArea.setPrefRowCount(3);
        noteTextArea.setWrapText(true);
        noteTextArea.getStyleClass().add("page-text-area");
        if (existingItem != null && existingItem.getNote() != null) {
            noteTextArea.setText(existingItem.getNote());
        }
        return createSection(title, noteTextArea);
    }

    private VBox createSection(Label title, Node content) {
        VBox section = new VBox(9, title, content);
        section.getStyleClass().add("order-option-section");
        return section;
    }

    private void updateQuantityLabel() {
        if (quantityValueLabel != null) {
            quantityValueLabel.setText(String.valueOf(currentQuantity));
        }
    }

    private void updateOptionChipStyles(ToggleGroup toggleGroup) {
        for (Toggle toggle : toggleGroup.getToggles()) {
            if (toggle instanceof ToggleButton button) {
                button.getStyleClass().remove("option-chip-active");
                if (toggle == toggleGroup.getSelectedToggle()) {
                    addStyleClass(button, "option-chip-active");
                }
            }
        }
    }

    private void handleAddCustomizedItem() {
        if (currentProduct == null) {
            return;
        }

        OrderItem item = editingItem == null ? new OrderItem() : editingItem;
        item.setProductId(currentProduct.getProductId());
        item.setProductName(currentProduct.getProductName());
        item.setCategory(currentProduct.getCategory());
        item.setBasePrice(currentProduct.getBasePrice());
        item.setQuantity(currentQuantity);
        item.setSugarLevel(currentProduct.isDrink() ? getSelectedToggleValue(sugarToggleGroup) : "");
        item.setIceLevel(currentProduct.isDrink() ? getSelectedToggleValue(iceToggleGroup) : "");
        item.setToppings(currentProduct.isDrink() ? collectSelectedToppings() : List.of());
        item.setNote(noteTextArea == null ? "" : safe(noteTextArea.getText()));
        item.setLineTotal(calculateItemTotal(item));

        if (editingItem == null) {
            cartItems.add(item);
        }

        renderCartItems();
        closeCustomizeProductDialog();
    }

    private List<ToppingItem> collectSelectedToppings() {
        List<ToppingItem> selectedToppings = new ArrayList<>();
        for (CheckBox checkBox : toppingCheckboxes) {
            if (checkBox.isSelected() && checkBox.getUserData() instanceof ProductOption topping) {
                selectedToppings.add(new ToppingItem(topping.getProductId(), topping.getProductName(), topping.getBasePrice()));
            }
        }
        return selectedToppings;
    }

    private String getSelectedToggleValue(ToggleGroup toggleGroup) {
        if (toggleGroup == null || toggleGroup.getSelectedToggle() == null) {
            return "";
        }
        Object value = toggleGroup.getSelectedToggle().getUserData();
        return value == null ? "" : value.toString();
    }

    private void handleEditCartItem(OrderItem item) {
        ProductOption product = products.stream()
                .filter(candidate -> Objects.equals(candidate.getProductId(), item.getProductId()))
                .findFirst()
                .orElse(new ProductOption(item.getProductId(), item.getProductName(), item.getCategory(), item.getBasePrice(), ProductStatus.AVAILABLE));

        currentProduct = product;
        editingItem = item;
        currentQuantity = item.getQuantity();
        renderCustomizeDialog(product, item);
        customizeOverlay.setManaged(true);
        customizeOverlay.setVisible(true);
    }

    private void handleRemoveCartItem(OrderItem item) {
        cartItems.remove(item);
        renderCartItems();
    }

    private BigDecimal calculateItemTotal(OrderItem item) {
        BigDecimal toppingTotal = item.getToppings().stream()
                .map(topping -> topping.getPrice() == null ? BigDecimal.ZERO : topping.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return item.getBasePrice()
                .add(toppingTotal)
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    }

    private BigDecimal calculateCartTotal() {
        return cartItems.stream()
                .map(item -> {
                    BigDecimal lineTotal = calculateItemTotal(item);
                    item.setLineTotal(lineTotal);
                    return lineTotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void renderCartItems() {
        BigDecimal cartTotal = calculateCartTotal();
        cartItemsBox.getChildren().clear();
        if (cartItems.isEmpty()) {
            Label emptyState = new Label("Chưa có món trong giỏ");
            emptyState.getStyleClass().add("cart-empty-label");
            cartItemsBox.getChildren().add(emptyState);
        } else {
            cartItemsBox.getChildren().addAll(cartItems.stream()
                    .map(this::createCartItemCard)
                    .toList());
        }
        totalLabel.setText(CurrencyFormatter.format(cartTotal));
    }

    private VBox createCartItemCard(OrderItem item) {
        Label itemName = new Label(item.getProductName() + " x" + item.getQuantity());
        itemName.getStyleClass().add("cart-item-name");
        itemName.setWrapText(true);

        Label linePrice = new Label(CurrencyFormatter.format(item.getLineTotal()));
        linePrice.getStyleClass().add("cart-item-price");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox header = new HBox(8, itemName, spacer, linePrice);
        header.setAlignment(Pos.TOP_LEFT);

        VBox optionLines = renderOrderItemOptions(item);

        Button editButton = new Button("Sửa");
        editButton.getStyleClass().add("cart-action-button");
        editButton.setOnAction(event -> handleEditCartItem(item));

        Button removeButton = new Button("Xóa");
        removeButton.getStyleClass().addAll("cart-action-button", "cart-action-button-danger");
        removeButton.setOnAction(event -> handleRemoveCartItem(item));

        HBox actions = new HBox(8, editButton, removeButton);
        actions.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(8, header, optionLines, actions);
        card.getStyleClass().add("cart-item-card");
        return card;
    }

    private VBox renderOrderItemOptions(OrderItem item) {
        VBox options = new VBox(3);
        String optionSummary = buildOptionSummary(item);
        if (!optionSummary.isBlank()) {
            options.getChildren().add(createCartDetailLabel(optionSummary));
        }

        String toppingSummary = item.getToppings().stream()
                .map(ToppingItem::getToppingName)
                .collect(Collectors.joining(", "));
        if (!toppingSummary.isBlank()) {
            options.getChildren().add(createCartDetailLabel("Topping: " + toppingSummary));
        }

        if (!safe(item.getNote()).isBlank()) {
            options.getChildren().add(createCartDetailLabel("Ghi chú: " + safe(item.getNote())));
        }
        return options;
    }

    private Label createCartDetailLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("cart-item-detail");
        label.setWrapText(true);
        return label;
    }

    private String buildOptionSummary(OrderItem item) {
        List<String> details = new ArrayList<>();
        if (!safe(item.getSugarLevel()).isBlank()) {
            details.add("Đường " + safe(item.getSugarLevel()));
        }
        if (!safe(item.getIceLevel()).isBlank()) {
            details.add(safe(item.getIceLevel()));
        }
        return String.join(" • ", details);
    }

    public String buildOrderDetailText(OrderItem item) {
        List<String> customization = new ArrayList<>();
        String optionSummary = buildOptionSummary(item);
        if (!optionSummary.isBlank()) {
            customization.add(optionSummary.replace(" • ", ", "));
        }

        String toppingSummary = item.getToppings().stream()
                .map(ToppingItem::getToppingName)
                .collect(Collectors.joining(", "));
        if (!toppingSummary.isBlank()) {
            customization.add("Topping: " + toppingSummary);
        }

        if (!safe(item.getNote()).isBlank()) {
            customization.add("Ghi chú: " + safe(item.getNote()));
        }

        String optionText = customization.isEmpty() ? "Không tùy chỉnh" : String.join(", ", customization);
        return "Món: " + item.getProductName()
                + " | Tùy chỉnh: " + optionText
                + " | SL: " + item.getQuantity()
                + " | Đơn giá: " + CurrencyFormatter.format(item.getBasePrice())
                + " | Thành tiền: " + CurrencyFormatter.format(item.getLineTotal());
    }

    private boolean hasTopping(OrderItem item, String toppingId) {
        return item.getToppings().stream()
                .anyMatch(topping -> Objects.equals(topping.getToppingId(), toppingId));
    }

    private void showInlineMessage(String message) {
        if (notificationDelay != null) {
            notificationDelay.stop();
        }
        notificationLabel.setText(message);
        notificationLabel.setManaged(true);
        notificationLabel.setVisible(true);

        notificationDelay = new PauseTransition(Duration.seconds(3.5));
        notificationDelay.setOnFinished(event -> {
            notificationLabel.setVisible(false);
            notificationLabel.setManaged(false);
        });
        notificationDelay.play();
    }

    private String normalize(String value) {
        String lower = safe(value).toLowerCase(Locale.ROOT);
        return Normalizer.normalize(lower, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private String formatMoneyCompact(BigDecimal amount) {
        long value = amount == null ? 0L : amount.longValue();
        return String.format(Locale.US, "%,d", value).replace(",", ".") + "đ";
    }

    private void addStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }
}
