CREATE DATABASE CoffeeShop

USE CoffeeShop

CREATE TABLE tblRoles(
	roleId nvarchar(10),
	roleName nvarchar(20),
	CONSTRAINT PK_tblRoles PRIMARY KEY (roleId)
);

CREATE TABLE tblUsers(
	userId nvarchar(10),
	userName nvarchar(30),
	userPhone nvarchar(11) CHECK(userPhone LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' 
		OR userPhone LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	userAddress nvarchar(50),
	userEmail nvarchar(30) CHECK(userEmail LIKE '%[A-Za-z0-9]@[A-Za-z0-9]%.[A-Za-z0-9]%'),
	userRoleId nvarchar(10),
	[password] nvarchar(20),
	CONSTRAINT PK_tblUsers PRIMARY KEY (userId),
	CONSTRAINT FK_tblUsers_tblRoles FOREIGN KEY (userRoleId) REFERENCES tblRoles(roleId)
);
CREATE TABLE tblTypes(
	typeId nvarchar(10),
	typeName nvarchar(30),
	CONSTRAINT PK_tblTypes PRIMARY KEY (typeId)
);

CREATE TABLE tblProducts(
	productId nvarchar(10),
	productTypeId nvarchar(10),
	productName nvarchar(30),
	productPrice DECIMAL(10,2),
	productQuantity INT,
	productImg nvarchar(255),
	CONSTRAINT PK_tblProducts PRIMARY KEY (productId),
	CONSTRAINT FK_tblProducts_tblTypes FOREIGN KEY (productTypeId) REFERENCES tblTypes(typeId)
);

CREATE TABLE tblOrders(
	orderId nvarchar(10),
	userId nvarchar(10),
	orderDate DATE CHECK(YEAR(orderDate) - YEAR(GETDATE()) <= 1),
	orderTotal DECIMAL(10,2),
	CONSTRAINT PK_tlbOrders PRIMARY KEY (orderId),
	CONSTRAINT FK_tblOrders_tblUsers FOREIGN KEY (userId) REFERENCES tblUsers(userId)
)

CREATE TABLE tblOrderDetails(
	detailId nvarchar(10),
	orderId nvarchar(10),
	productId nvarchar(10),
	productPrice DECIMAL(10,2),
	orderQuantity INT,
	CONSTRAINT PK_tblOrderDetails PRIMARY KEY (detailId),
	CONSTRAINT FK_tblOrderDetails_tblOrders FOREIGN KEY (orderId) REFERENCES tblOrders(orderId),
	CONSTRAINT FK_tblOrders_tblProducts FOREIGN KEY (productId) REFERENCES tblProducts(productId)
)

