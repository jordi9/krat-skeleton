ALTER TABLE items RENAME TO items_old;

CREATE TABLE items (
  id              TEXT    PRIMARY KEY,
  name            TEXT    NOT NULL,
  description     TEXT,
  priority        TEXT    NOT NULL,
  created_at      INTEGER NOT NULL,
  updated_at      INTEGER NOT NULL
);

INSERT INTO items (id, name, description, priority, created_at, updated_at)
SELECT
  'item_' || substr('000000000000000000000' || id, -21),
  name,
  description,
  'normal',
  created_at,
  updated_at
FROM items_old;

DROP TABLE items_old;
