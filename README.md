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

# Preview
### Página Principal
![image](https://github.com/user-attachments/assets/a1933cf3-acc0-49e8-a2b2-3ebc3085183f)

### Página de Login
![image](https://github.com/user-attachments/assets/8d22a7ea-bea0-4b95-804f-0690e7c7b606)

### Detalhes do Produto
![image](https://github.com/user-attachments/assets/9b51f569-157b-424d-8d22-18fb9e211c92)

### Cadastro de Produto
![image](https://github.com/user-attachments/assets/8691c181-62f3-4485-84ac-75b0cc65c881)

### Cadastro de Estoque
![image](https://github.com/user-attachments/assets/b47ffbc4-7e31-4f99-9019-e5d776453300)

### Cadastro de Promoção
![image](https://github.com/user-attachments/assets/8b2517b0-cf14-4244-80bd-b12af68976ff)

### Relatório de Vendas
![image](https://github.com/user-attachments/assets/5483038b-8912-4b7a-9d7a-ad9180863eaa)

### Perfil
![image](https://github.com/user-attachments/assets/3c903149-1af8-4bea-86b8-3b78c6c05de7)

### Cadastro de Endereço
![image](https://github.com/user-attachments/assets/b05ccf2b-db8b-44a6-8557-2af227e61bf5)

### Pedidos
![image](https://github.com/user-attachments/assets/d7f63bdb-8beb-4492-a828-670e4e42692c)

### Feedbacks
![image](https://github.com/user-attachments/assets/b04fe7b9-b7a6-4ed3-b794-7fa8b92f842e)

