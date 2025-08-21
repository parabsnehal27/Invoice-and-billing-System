<h1>Invoice & Billing Automation with PDF Export</h1>

<p>Project Overview
This is a Java-based application that automates invoice and billing management.<br>  
It allows users to create invoices and export invoices in <b>PDF format</b>. <br> 
Additionally, invoices can be sent to clients via <b>email </b> directly from the application.  
</p>

<h2>Tech Stack</h2>
<p>- Java (Swing) – GUI Development  <br>
- MySQL  – Database for storing invoices<br>
- JDBC – Database connectivity  <br>
- iText – PDF invoice generation  <br>
- JavaMail API – Email functionality  </p>

<h4>Steps to run the project:</h4>
<p>1. Import the project in Eclipse</p>
<p>2. Add require jar files(itext, JavaMail, JDBC Connector in lib\ </p>
<p>3. setup the database using following:</p>

```
DROP TABLE IF EXISTS `invoices`;
CREATE TABLE `invoices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_name` varchar(100) DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `gst_rate` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

<p>4. Run the project from main.java</p>
