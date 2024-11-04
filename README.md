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

# Preview
Imagens da aplicação