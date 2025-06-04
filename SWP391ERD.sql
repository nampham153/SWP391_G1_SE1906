ALTER TABLE Staff DROP FOREIGN KEY FKStaff401300;
ALTER TABLE `Order` DROP FOREIGN KEY FKOrder835073;
ALTER TABLE ProductSpec DROP FOREIGN KEY FKProductSpe523014;
ALTER TABLE Product DROP FOREIGN KEY FKProduct482496;
ALTER TABLE Component DROP FOREIGN KEY FKComponent163002;
ALTER TABLE ComponentSpec DROP FOREIGN KEY FKComponentS495364;
ALTER TABLE ProductComponent DROP FOREIGN KEY FKProductCom260425;
ALTER TABLE ProductComponent DROP FOREIGN KEY FKProductCom262616;
ALTER TABLE Wishlist DROP FOREIGN KEY FKWishlist62691;
ALTER TABLE Wishlist DROP FOREIGN KEY FKWishlist408427;
ALTER TABLE Review DROP FOREIGN KEY FKReview102331;
ALTER TABLE CustomerAddress DROP FOREIGN KEY FKCustomerAd734436;
ALTER TABLE Staff DROP FOREIGN KEY FKStaff856296;
ALTER TABLE Component DROP FOREIGN KEY FKComponent395109;
ALTER TABLE Product DROP FOREIGN KEY FKProduct255424;
ALTER TABLE Account DROP FOREIGN KEY FKAccount757330;
ALTER TABLE Staff DROP FOREIGN KEY FKStaff725745;
ALTER TABLE Customer DROP FOREIGN KEY FKCustomer525761;
ALTER TABLE ItemImage DROP FOREIGN KEY FKItemImage681944;
ALTER TABLE Comment DROP FOREIGN KEY FKComment535817;
ALTER TABLE Comment DROP FOREIGN KEY FKComment510850;
ALTER TABLE BrandComponentCategory DROP FOREIGN KEY FKBrandCompo103375;
ALTER TABLE BrandComponentCategory DROP FOREIGN KEY FKBrandCompo758712;
ALTER TABLE BrandProductCategory DROP FOREIGN KEY FKBrandProdu13532;
ALTER TABLE BrandProductCategory DROP FOREIGN KEY FKBrandProdu888951;
ALTER TABLE SupportTicket DROP FOREIGN KEY FKSupportTic797377;
ALTER TABLE SupportTicket DROP FOREIGN KEY FKSupportTic396657;
ALTER TABLE Staff DROP FOREIGN KEY FKStaff394611;
ALTER TABLE ComponentSpecDetail DROP FOREIGN KEY FKComponentS774726;
ALTER TABLE ComponentSpecDetail DROP FOREIGN KEY FKComponentS202225;
ALTER TABLE ProductSpecDetail DROP FOREIGN KEY FKProductSpe101170;
ALTER TABLE ProductSpecDetail DROP FOREIGN KEY FKProductSpe384127;
ALTER TABLE ItemOrder DROP FOREIGN KEY FKItemOrder430995;
ALTER TABLE ItemOrder DROP FOREIGN KEY FKItemOrder509026;
ALTER TABLE Review DROP FOREIGN KEY FKReview50645;
ALTER TABLE AccessPath DROP FOREIGN KEY FKAccessPath742010;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS `Order`;
DROP TABLE IF EXISTS Staff;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS ProductCategory;
DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS ProductSpec;
DROP TABLE IF EXISTS Component;
DROP TABLE IF EXISTS ComponentCategory;
DROP TABLE IF EXISTS ComponentSpec;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS ProductComponent;
DROP TABLE IF EXISTS Wishlist;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS CustomerAddress;
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS ItemImage;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS BrandComponentCategory;
DROP TABLE IF EXISTS BrandProductCategory;
DROP TABLE IF EXISTS SupportTicket;
DROP TABLE IF EXISTS SupportCategory;
DROP TABLE IF EXISTS ComponentSpecDetail;
DROP TABLE IF EXISTS ProductSpecDetail;
DROP TABLE IF EXISTS ItemOrder;
DROP TABLE IF EXISTS AccessPath;
CREATE TABLE Customer (
  CustomerId        varchar(255) NOT NULL, 
  CustomerName      varchar(255) NOT NULL, 
  CustomerEmail     varchar(255), 
  CustomerBirthDate date, 
  CustomerGender    bit(1) NOT NULL, 
  Status            bit(1), 
  PRIMARY KEY (CustomerId)) CHARACTER SET UTF8;
CREATE TABLE Product (
  ProductId  varchar(255) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (ProductId)) CHARACTER SET UTF8;
CREATE TABLE `Order` (
  OrderId          int(10) NOT NULL AUTO_INCREMENT, 
  OrderDate        date NOT NULL, 
  OrderAddress     varchar(255) NOT NULL, 
  OrderPhone       varchar(255) NOT NULL, 
  OrderEmail       varchar(255), 
  `Shipping Fee`   decimal(19, 0) NOT NULL, 
  `Additional Fee` decimal(19, 0) NOT NULL, 
  Total            decimal(19, 0) NOT NULL, 
  OrderStatus      bit(1) NOT NULL, 
  Note             varchar(255), 
  CustomerId       varchar(255), 
  PRIMARY KEY (OrderId)) CHARACTER SET UTF8;
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
  PRIMARY KEY (StaffId)) CHARACTER SET UTF8;
CREATE TABLE Role (
  RoleId   int(10) NOT NULL AUTO_INCREMENT, 
  RoleName varchar(255) NOT NULL, 
  PRIMARY KEY (RoleId)) CHARACTER SET UTF8;
CREATE TABLE ProductCategory (
  CategoryId   int(10) NOT NULL AUTO_INCREMENT, 
  CategoryName varchar(255) NOT NULL, 
  PRIMARY KEY (CategoryId)) CHARACTER SET UTF8;
CREATE TABLE Brand (
  BrandId   int(10) NOT NULL, 
  BrandName varchar(255) NOT NULL, 
  PRIMARY KEY (BrandId)) CHARACTER SET UTF8;
CREATE TABLE ProductSpec (
  SpecId     int(10) NOT NULL AUTO_INCREMENT, 
  SpecName   varchar(255) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (SpecId)) CHARACTER SET UTF8;
CREATE TABLE Component (
  ComponentId varchar(255) NOT NULL, 
  CategoryId  int(10) NOT NULL, 
  PRIMARY KEY (ComponentId)) CHARACTER SET UTF8;
CREATE TABLE ComponentCategory (
  CategoryId   int(10) NOT NULL AUTO_INCREMENT, 
  CategoryName varchar(255) NOT NULL, 
  PRIMARY KEY (CategoryId)) CHARACTER SET UTF8;
CREATE TABLE ComponentSpec (
  SpecId     int(10) NOT NULL AUTO_INCREMENT, 
  SpecName   varchar(255) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (SpecId)) CHARACTER SET UTF8;
CREATE TABLE Item (
  SerialNumber varchar(255) NOT NULL, 
  ItemName     varchar(255) NOT NULL, 
  Stock        int(10) NOT NULL, 
  Price        decimal(19, 0) NOT NULL, 
  Views        int(10) NOT NULL, 
  PRIMARY KEY (SerialNumber)) CHARACTER SET UTF8;
CREATE TABLE ProductComponent (
  ProductId   varchar(255) NOT NULL, 
  ComponentId varchar(255) NOT NULL, 
  Quantity    int(10) NOT NULL, 
  PRIMARY KEY (ProductId, 
  ComponentId)) CHARACTER SET UTF8;
CREATE TABLE Wishlist (
  UserId      varchar(255) NOT NULL, 
  InventoryId varchar(255) NOT NULL, 
  ItemRank    int(10) NOT NULL, 
  DateAdded   date NOT NULL, 
  PRIMARY KEY (UserId, 
  InventoryId)) CHARACTER SET UTF8;
CREATE TABLE Review (
  ReviewId      int(10) NOT NULL AUTO_INCREMENT, 
  ReviewContent varchar(255) NOT NULL, 
  ReviewImage   varchar(255), 
  ReviewRating  int(10) NOT NULL, 
  ReviewDate    timestamp NOT NULL, 
  CustomerId    varchar(255) NOT NULL, 
  ItemId        varchar(255) NOT NULL, 
  OrderId       int(10) NOT NULL, 
  PRIMARY KEY (ReviewId)) CHARACTER SET UTF8;
CREATE TABLE CustomerAddress (
  CustomerAddressId int(10) NOT NULL AUTO_INCREMENT, 
  CustomerAddress   varchar(255) NOT NULL, 
  CustomerId        varchar(255) NOT NULL, 
  PRIMARY KEY (CustomerAddressId)) CHARACTER SET UTF8;
CREATE TABLE Department (
  DepartmentId   int(10) NOT NULL AUTO_INCREMENT, 
  DepartmentName varchar(255) NOT NULL, 
  PRIMARY KEY (DepartmentId)) CHARACTER SET UTF8;
CREATE TABLE Account (
  Phone    varchar(255) NOT NULL, 
  Password varchar(255) NOT NULL, 
  RoleId   int(10) NOT NULL, 
  PRIMARY KEY (Phone)) CHARACTER SET UTF8;
CREATE TABLE ItemImage (
  ImageId      int(10) NOT NULL AUTO_INCREMENT, 
  ImageContent varchar(255) NOT NULL, 
  InventoryId  varchar(255) NOT NULL, 
  PRIMARY KEY (ImageId)) CHARACTER SET UTF8;
CREATE TABLE Comment (
  CommentId      int(10) NOT NULL AUTO_INCREMENT, 
  CommentContent varchar(255) NOT NULL, 
  CommentImage   varchar(255), 
  CommenterName  varchar(255) NOT NULL, 
  CustomerId     varchar(255), 
  InventoryId    varchar(255) NOT NULL, 
  PRIMARY KEY (CommentId)) CHARACTER SET UTF8;
CREATE TABLE BrandComponentCategory (
  BrandId    int(10) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (BrandId, 
  CategoryId)) CHARACTER SET UTF8;
CREATE TABLE BrandProductCategory (
  BrandId    int(10) NOT NULL, 
  CategoryId int(10) NOT NULL, 
  PRIMARY KEY (BrandId, 
  CategoryId)) CHARACTER SET UTF8;
CREATE TABLE SupportTicket (
  TicketId      int(10) NOT NULL AUTO_INCREMENT, 
  TicketContent varchar(255) NOT NULL, 
  IssueDate     date NOT NULL, 
  TicketStatus  bit(1) NOT NULL, 
  Attachment    blob, 
  CustomerId    varchar(255) NOT NULL, 
  CategoryId    int(10) NOT NULL, 
  PRIMARY KEY (TicketId)) CHARACTER SET UTF8;
CREATE TABLE SupportCategory (
  CategoryId   int(10) NOT NULL AUTO_INCREMENT, 
  CategoryName varchar(255) NOT NULL, 
  PRIMARY KEY (CategoryId)) CHARACTER SET UTF8;
CREATE TABLE ComponentSpecDetail (
  ComponentId varchar(255) NOT NULL, 
  SpecId      int(10) NOT NULL, 
  SpecDetail  varchar(255) NOT NULL, 
  PRIMARY KEY (ComponentId, 
  SpecId)) CHARACTER SET UTF8;
CREATE TABLE ProductSpecDetail (
  ProductId  varchar(255) NOT NULL, 
  SpecId     int(10) NOT NULL, 
  SpecDetail varchar(255) NOT NULL, 
  PRIMARY KEY (ProductId, 
  SpecId)) CHARACTER SET UTF8;
CREATE TABLE ItemOrder (
  ItemId    varchar(255) NOT NULL, 
  OrderId   int(10) NOT NULL, 
  Quantity  int(11) NOT NULL, 
  ListPrice decimal(19, 0) NOT NULL, 
  PRIMARY KEY (ItemId, 
  OrderId)) CHARACTER SET UTF8;
CREATE TABLE AccessPath (
  Path   varchar(255) NOT NULL, 
  RoleId int(10), 
  PRIMARY KEY (Path)) CHARACTER SET UTF8;
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
ALTER TABLE ItemImage ADD CONSTRAINT FKItemImage681944 FOREIGN KEY (InventoryId) REFERENCES Item (SerialNumber);
ALTER TABLE Comment ADD CONSTRAINT FKComment535817 FOREIGN KEY (CustomerId) REFERENCES Customer (CustomerId);
ALTER TABLE Comment ADD CONSTRAINT FKComment510850 FOREIGN KEY (InventoryId) REFERENCES Item (SerialNumber);
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
ALTER TABLE AccessPath ADD CONSTRAINT FKAccessPath742010 FOREIGN KEY (RoleId) REFERENCES Role (RoleId);
