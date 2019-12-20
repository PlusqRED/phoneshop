drop table if exists phone2color;
drop table if exists colors;
drop table if exists stocks;
drop table if exists phones;
drop table if exists ORDERS;
drop table if exists ORDER_ITEMS;
drop table if exists USERS;

create table USERS
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    username varchar(255) not null,
    password varchar(255) not null,
    role     varchar(255) not null
);

insert into USERS
values (default, 'admin', 12345678, 'ROLE_ADMIN');

create table colors
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50),
    UNIQUE (code)
);

create table ORDERS
(
    ID                     BIGINT auto_increment primary key,
    SUBTOTAL               DOUBLE      not null,
    DELIVERY_PRICE         DOUBLE      not null,
    OVERALL_WRAPPING_PRICE DOUBLE      not null,
    FIRST_NAME             VARCHAR(30) not null,
    LAST_NAME              VARCHAR(30) not null,
    DELIVERY_ADDRESS       VARCHAR(50) not null,
    CONTACT_PHONE_NO       VARCHAR(50) not null,
    ADDITIONAL_INFO        VARCHAR(512),
    ORDER_STATUS           VARCHAR(20) not null,
    DATE                   TIMESTAMP   not null
);



create table ORDER_ITEMS
(
    ORDER_ID            BIGINT  not null,
    PHONE_ID            BIGINT  not null,
    ID                  BIGINT AUTO_INCREMENT primary key,
    QUANTITY            INTEGER not null,
    WRAPPING            BOOLEAN default 'false',
    WRAPPING_ADDITIONAL varchar(30)
);

create table phones
(
    id                    BIGINT AUTO_INCREMENT primary key,
    brand                 VARCHAR(50)  NOT NULL,
    model                 VARCHAR(254) NOT NULL,
    price                 FLOAT,
    displaySizeInches     FLOAT,
    weightGr              SMALLINT,
    lengthMm              FLOAT,
    widthMm               FLOAT,
    heightMm              FLOAT,
    announced             DATETIME,
    deviceType            VARCHAR(50),
    os                    VARCHAR(100),
    displayResolution     VARCHAR(50),
    pixelDensity          SMALLINT,
    displayTechnology     VARCHAR(50),
    backCameraMegapixels  FLOAT,
    frontCameraMegapixels FLOAT,
    ramGb                 FLOAT,
    internalStorageGb     FLOAT,
    batteryCapacityMah    SMALLINT,
    talkTimeHours         FLOAT,
    standByTimeHours      FLOAT,
    bluetooth             VARCHAR(50),
    positioning           VARCHAR(100),
    imageUrl              VARCHAR(254),
    description           VARCHAR(4096),
    CONSTRAINT UC_phone UNIQUE (brand, model)
);

create table phone2color
(
    phoneId BIGINT,
    colorId BIGINT,
    CONSTRAINT FK_phone2color_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_phone2color_colorId FOREIGN KEY (colorId) REFERENCES colors (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table stocks
(
    phoneId  BIGINT   NOT NULL,
    stock    SMALLINT NOT NULL,
    reserved SMALLINT NOT NULL,
    UNIQUE (phoneId),
    CONSTRAINT FK_stocks_phoneId FOREIGN KEY (phoneId) REFERENCES phones (id) ON DELETE CASCADE ON UPDATE CASCADE
);