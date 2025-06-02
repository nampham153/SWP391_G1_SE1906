CREATE TABLE Customer (
  CustomerId        varchar(255) NOT NULL, 
  CustomerName      varchar(255) NOT NULL, 
  CustomerEmail     varchar(255), 
  CustomerBirthDate date, 
  CustomerGender    bit(1) NOT NULL, 
  Status            bit(1), 
  PRIMARY KEY (CustomerId));
CREATE TABLE Product (
  ProductId  varchar(255) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (ProductId));
CREATE TABLE `Order` (
  OrderId          int(10) NOT NULL AUTO_INCREMENT, 
  OrderDate        date NOT NULL, 
  OrderAddress     varchar(255) NOT NULL, 
  OrderPhone       varchar(255) NOT NULL, 
  OrderEmail       varchar(255), 
  `Shipping Fee`   real NOT NULL, 
  `Additional Fee` real NOT NULL, 
  Total            real NOT NULL, 
  OrderStatus      bit(1) NOT NULL, 
  Note             varchar(255), 
  CustomerId       varchar(255), 
  PRIMARY KEY (OrderId));
CREATE TABLE Staff (
  StaffId        varchar(255) NOT NULL, 
  StaffName      varchar(255) NOT NULL, 
  StaffTitle     varchar(255) NOT NULL, 
  StaffAddress   varchar(255) NOT NULL, 
  StaffBirthDate date NOT NULL, 
  StaffGender    bit(1) NOT NULL, 
  SupervisorId   varchar(255), 
  DepartmentId   int(10) NOT NULL, 
  Status         bit(1), 
  PRIMARY KEY (StaffId));
CREATE TABLE Role (
  RoleId   int(10) NOT NULL AUTO_INCREMENT, 
  RoleName varchar(255) NOT NULL, 
  PRIMARY KEY (RoleId));
CREATE TABLE ProductCategory (
  CategoryId   int(10) NOT NULL AUTO_INCREMENT, 
  CategoryName varchar(255) NOT NULL, 
  PRIMARY KEY (CategoryId));
CREATE TABLE Brand (
  BrandId   int(10) NOT NULL AUTO_INCREMENT, 
  BrandName varchar(255) NOT NULL, 
  PRIMARY KEY (BrandId));
CREATE TABLE ProductSpec (
  SpecId     int(10) NOT NULL AUTO_INCREMENT, 
  SpecName   varchar(255) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (SpecId));
CREATE TABLE Component (
  ComponentId varchar(255) NOT NULL, 
  CategoryId  int(10) NOT NULL, 
  PRIMARY KEY (ComponentId));
CREATE TABLE ComponentCategory (
  CategoryId   int(10) NOT NULL AUTO_INCREMENT, 
  CategoryName varchar(255) NOT NULL, 
  PRIMARY KEY (CategoryId));
CREATE TABLE ComponentSpec (
  SpecId     int(10) NOT NULL AUTO_INCREMENT, 
  SpecName   varchar(255) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (SpecId));
CREATE TABLE Item (
  SerialNumber varchar(255) NOT NULL, 
  ItemName     varchar(255) NOT NULL, 
  Stock        int(10) NOT NULL, 
  Price        real NOT NULL, 
  Views        int(10) NOT NULL, 
  CategoryId   int(10) NOT NULL, 
  PRIMARY KEY (SerialNumber));
CREATE TABLE ProductComponent (
  ProductId   varchar(255) NOT NULL, 
  ComponentId varchar(255) NOT NULL, 
  Quantity    int(10) NOT NULL, 
  PRIMARY KEY (ProductId, 
  ComponentId));
CREATE TABLE Wishlist (
  UserId      varchar(255) NOT NULL, 
  InventoryId varchar(255) NOT NULL, 
  Rank        int(10) NOT NULL, 
  DateAdded   int(10) NOT NULL, 
  PRIMARY KEY (UserId, 
  InventoryId));
CREATE TABLE Review (
  ReviewId      int(10) NOT NULL AUTO_INCREMENT, 
  ReviewContent varchar(255) NOT NULL, 
  ReviewImage   blob, 
  ReviewRating  int(10) NOT NULL, 
  ReviewDate    timestamp NOT NULL, 
  CustomerId    varchar(255) NOT NULL, 
  ItemId        varchar(255) NOT NULL, 
  OrderId       int(10) NOT NULL, 
  PRIMARY KEY (ReviewId));
CREATE TABLE CustomerAddress (
  CustomerAddressId      int(10) NOT NULL AUTO_INCREMENT, 
  CustomerAddressContent varchar(255) NOT NULL, 
  CustomerId             varchar(255) NOT NULL, 
  PRIMARY KEY (CustomerAddressId));
CREATE TABLE Department (
  DepartmentId   int(10) NOT NULL AUTO_INCREMENT, 
  DepartmentName varchar(255), 
  PRIMARY KEY (DepartmentId));
CREATE TABLE Account (
  Phone    varchar(255) NOT NULL, 
  Password varchar(255) NOT NULL, 
  RoleId   int(10) NOT NULL, 
  Status   bit(1), 
  PRIMARY KEY (Phone));
CREATE TABLE InventoryImage (
  ImageId      int(10) NOT NULL AUTO_INCREMENT, 
  ImageContent blob NOT NULL, 
  InventoryId  varchar(255) NOT NULL, 
  PRIMARY KEY (ImageId));
CREATE TABLE Comment (
  CommentId      int(10) NOT NULL AUTO_INCREMENT, 
  CommentContent varchar(255) NOT NULL, 
  CommentImage   blob, 
  CommenterName  varchar(255) NOT NULL, 
  CustomerId     varchar(255), 
  InventoryId    varchar(255) NOT NULL, 
  PRIMARY KEY (CommentId));
CREATE TABLE ItemCategory (
  CategoryId   int(10) NOT NULL AUTO_INCREMENT, 
  CategoryName varchar(255) NOT NULL, 
  PRIMARY KEY (CategoryId));
CREATE TABLE BrandComponentCategory (
  BrandId    int(10) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (BrandId, 
  CategoryId));
CREATE TABLE BrandProductCategory (
  BrandId    int(10) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (BrandId, 
  CategoryId));
CREATE TABLE SupportTicket (
  TicketId      int(10) NOT NULL AUTO_INCREMENT, 
  TicketContent varchar(255) NOT NULL, 
  IssueDate     date NOT NULL, 
  TicketStatus  bit(1) NOT NULL, 
  Attachment    blob, 
  CustomerId    varchar(255) NOT NULL, 
  CategoryId    int(10) NOT NULL, 
  PRIMARY KEY (TicketId));
CREATE TABLE SupportCategory (
  CategoryId   int(10) NOT NULL AUTO_INCREMENT, 
  CategoryName varchar(255) NOT NULL, 
  PRIMARY KEY (CategoryId));
CREATE TABLE ComponentSpecDetail (
  ComponentId varchar(255) NOT NULL, 
  SpecId      int(10) NOT NULL, 
  SpecDetail  varchar(255) NOT NULL, 
  PRIMARY KEY (ComponentId, 
  SpecId));
CREATE TABLE ProductSpecDetail (
  ProductId  varchar(255) NOT NULL, 
  SpecId     int(10) NOT NULL, 
  SpecDetail varchar(255) NOT NULL, 
  PRIMARY KEY (ProductId, 
  SpecId));
CREATE TABLE ItemOrder (
  ItemId    varchar(255) NOT NULL, 
  OrderId   int(10) NOT NULL, 
  Quantity  int(11) NOT NULL, 
  ListPrice real NOT NULL, 
  PRIMARY KEY (ItemId, 
  OrderId));
ALTER TABLE Staff ADD CONSTRAINT FKStaff401300 FOREIGN KEY (StaffId) REFERENCES Customer (CustomerId);
ALTER TABLE `Order` ADD CONSTRAINT FKOrder835073 FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId);
ALTER TABLE ProductSpec ADD CONSTRAINT FKProductSpe523014 FOREIGN KEY (CategoryId) REFERENCES ProductCategory (CategoryId);
ALTER TABLE Product ADD CONSTRAINT FKProduct482496 FOREIGN KEY (CategoryId) REFERENCES ProductCategory (CategoryId);
ALTER TABLE Component ADD CONSTRAINT FKComponent163002 FOREIGN KEY (CategoryId) REFERENCES ComponentCategory (CategoryId);
ALTER TABLE ComponentSpec ADD CONSTRAINT FKComponentS495364 FOREIGN KEY (CategoryId) REFERENCES ComponentCategory (CategoryId);
ALTER TABLE ProductComponent ADD CONSTRAINT FKProductCom260425 FOREIGN KEY (ProductId) REFERENCES Product (ProductId);
ALTER TABLE ProductComponent ADD CONSTRAINT FKProductCom262616 FOREIGN KEY (ComponentId) REFERENCES Component (ComponentId);
ALTER TABLE Wishlist ADD CONSTRAINT FKWishlist62691 FOREIGN KEY (UserId) REFERENCES Customer (CustomerId);
ALTER TABLE Wishlist ADD CONSTRAINT FKWishlist408427 FOREIGN KEY (InventoryId) REFERENCES Item (SerialNumber);
ALTER TABLE Review ADD CONSTRAINT FKReview102331 FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId);
ALTER TABLE CustomerAddress ADD CONSTRAINT FKCustomerAd734436 FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId);
ALTER TABLE Staff ADD CONSTRAINT FKStaff856296 FOREIGN KEY (DepartmentId) REFERENCES Department (DepartmentId);
ALTER TABLE Component ADD CONSTRAINT FKComponent395109 FOREIGN KEY (ComponentId) REFERENCES Item (SerialNumber);
ALTER TABLE Product ADD CONSTRAINT FKProduct255424 FOREIGN KEY (ProductId) REFERENCES Item (SerialNumber);
ALTER TABLE Account ADD CONSTRAINT FKAccount757330 FOREIGN KEY (RoleId) REFERENCES Role (RoleId);
ALTER TABLE Staff ADD CONSTRAINT FKStaff725745 FOREIGN KEY (StaffId) REFERENCES Account (Phone);
ALTER TABLE Customer ADD CONSTRAINT FKCustomer525761 FOREIGN KEY (CustomerId) REFERENCES Account (Phone);
ALTER TABLE InventoryImage ADD CONSTRAINT FKInventoryI744557 FOREIGN KEY (InventoryId) REFERENCES Item (SerialNumber);
ALTER TABLE Comment ADD CONSTRAINT FKComment535817 FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId);
ALTER TABLE Comment ADD CONSTRAINT FKComment510850 FOREIGN KEY (InventoryId) REFERENCES Item (SerialNumber);
ALTER TABLE Item ADD CONSTRAINT FKItem390998 FOREIGN KEY (CategoryId) REFERENCES ItemCategory (CategoryId);
ALTER TABLE BrandComponentCategory ADD CONSTRAINT FKBrandCompo103375 FOREIGN KEY (BrandId) REFERENCES Brand (BrandId);
ALTER TABLE BrandComponentCategory ADD CONSTRAINT FKBrandCompo758712 FOREIGN KEY (CategoryId) REFERENCES ComponentCategory (CategoryId);
ALTER TABLE BrandProductCategory ADD CONSTRAINT FKBrandProdu13532 FOREIGN KEY (BrandId) REFERENCES Brand (BrandId);
ALTER TABLE BrandProductCategory ADD CONSTRAINT FKBrandProdu888951 FOREIGN KEY (CategoryId) REFERENCES ProductCategory (CategoryId);
ALTER TABLE SupportTicket ADD CONSTRAINT FKSupportTic797377 FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId);
ALTER TABLE SupportTicket ADD CONSTRAINT FKSupportTic396657 FOREIGN KEY (CategoryId) REFERENCES SupportCategory (CategoryId);
ALTER TABLE Staff ADD CONSTRAINT FKStaff394611 FOREIGN KEY (SupervisorId) REFERENCES Staff (StaffId);
ALTER TABLE ComponentSpecDetail ADD CONSTRAINT FKComponentS774726 FOREIGN KEY (ComponentId) REFERENCES Component (ComponentId);
ALTER TABLE ComponentSpecDetail ADD CONSTRAINT FKComponentS202225 FOREIGN KEY (SpecId) REFERENCES ComponentSpec (SpecId);
ALTER TABLE ProductSpecDetail ADD CONSTRAINT FKProductSpe101170 FOREIGN KEY (ProductId) REFERENCES Product (ProductId);
ALTER TABLE ProductSpecDetail ADD CONSTRAINT FKProductSpe384127 FOREIGN KEY (SpecId) REFERENCES ProductSpec (SpecId);
ALTER TABLE ItemOrder ADD CONSTRAINT FKItemOrder430995 FOREIGN KEY (ItemId) REFERENCES Item (SerialNumber);
ALTER TABLE ItemOrder ADD CONSTRAINT FKItemOrder509026 FOREIGN KEY (OrderId) REFERENCES `Order` (OrderId);
ALTER TABLE Review ADD CONSTRAINT FKReview50645 FOREIGN KEY (ItemId, OrderId) REFERENCES ItemOrder (ItemId, OrderId);
