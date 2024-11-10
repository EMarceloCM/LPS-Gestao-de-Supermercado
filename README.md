# SigmaMarket Σ
<h1>Informações Importantes</h1>
<h2>Evento que desativa uma promoção quando seu tempo limite exiprar</h2>

```
DELIMITER $$
CREATE EVENT IF NOT EXISTS deactivate_expired_promotions
ON SCHEDULE EVERY 15 SECOND
DO
BEGIN
    UPDATE Promotion
    SET `active` = 0
    WHERE `active` = 1 AND date_add(creationDate, INTERVAL durationMinutes MINUTE) <= NOW() AND id <> 0;
END;
$$
DELIMITER ;
```

</br>
<h2>Trigger que reduz o estoque de um produto após uma venda dele</h2>

```
DELIMITER $$

CREATE TRIGGER reduce_stock_after_order
AFTER INSERT ON ItemOrder
FOR EACH ROW
BEGIN
    UPDATE Product
    SET stock = stock - NEW.quantity
    WHERE id = NEW.product_id;
END$$

DELIMITER ;
```

<br>
<h2>Trigger que aumenta o estoque de um produto após um registro de compra ou atualização da mesma</h2>

```
DELIMITER //

CREATE TRIGGER update_product_stock_after_insert
AFTER INSERT ON ProductSupplier
FOR EACH ROW
BEGIN
    UPDATE Product
    SET stock = stock + NEW.quantity
    WHERE id = NEW.product_id;
END//

DELIMITER ;
```

```
DELIMITER //

CREATE TRIGGER update_product_stock_after_update
AFTER UPDATE ON ProductSupplier
FOR EACH ROW
BEGIN
    UPDATE Product
    SET stock = stock + NEW.quantity
    WHERE id = NEW.product_id;
END//

DELIMITER ;
```
</br>
<h2>Trigger que desativa uma promoção ativa de um produto caso uma outra promoção seja cadastrada naquele mesmo produto ou uma promoção existe seja reativada</h2>

```
DELIMITER $$

CREATE TRIGGER trg_deactivate_other_promotions
BEFORE INSERT ON Promotion
FOR EACH ROW
BEGIN
    IF NEW.active = 1 THEN
        UPDATE Promotion
        SET active = 0
        WHERE product_id = NEW.product_id
          AND id <> NEW.id
          AND active = 1;
    END IF;
END$$

DELIMITER ;
```

```
DELIMITER $$

CREATE TRIGGER trg_deactivate_other_promotions_after_update
BEFORE UPDATE ON Promotion
FOR EACH ROW
BEGIN
    IF NEW.active = 1 THEN
        UPDATE Promotion
        SET active = 0
        WHERE product_id = NEW.product_id
          AND id <> NEW.id
          AND active = 1;
    END IF;
END$$

DELIMITER ;
```

# Preview
Imagens da aplicação
