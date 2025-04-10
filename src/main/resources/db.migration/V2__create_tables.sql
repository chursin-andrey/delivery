CREATE TABLE IF NOT EXISTS transports
(
    id      UUID        PRIMARY KEY,
    name    TEXT        NOT NULL,
    speed   INTEGER     NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id              UUID        PRIMARY KEY,
    location_x      INTEGER     NOT NULL,
    location_y      INTEGER     NOT NULL,
    status          TEXT        NOT NULL,
    courier_id      UUID,
    FOREIGN KEY (courier_id) REFERENCES couriers (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS couriers
(
    id              UUID       PRIMARY KEY,
    name            TEXT       NOT NULL,
    location_x      INTEGER    NOT NULL,
    location_y      INTEGER    NOT NULL,
    status          TEXT       NOT NULL,
    transport_id    UUID       NOT NULL,
    FOREIGN KEY (transport_id) REFERENCES transports (id)
        ON DELETE CASCADE
);
