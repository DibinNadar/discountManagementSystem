CREATE TABLE IF NOT EXISTS coupon (
  couponId VARCHAR(100) NOT NULL,
  percentageDiscount decimal(10,2) NOT NULL DEFAULT '0.00',
  upperAmountLimit int NOT NULL,
  globalUsageLimit int NOT NULL ,
  startDate date NOT NULL,
  expiryDate date NOT NULL,
  PRIMARY KEY (couponId),
  UNIQUE KEY couponId_UNIQUE (couponId)
);

CREATE TABLE IF NOT EXISTS customer (
  customerId INTEGER NOT NULL ,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (customerId),
  UNIQUE KEY customerId_UNIQUE (customerId)
);

CREATE TABLE IF NOT EXISTS customers_coupons (
  couponId VARCHAR(100) NOT NULL,
  customerId INTEGER NOT NULL ,
  PRIMARY KEY (couponId, customerId)
);

