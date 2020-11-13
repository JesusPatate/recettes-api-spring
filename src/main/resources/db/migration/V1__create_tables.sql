CREATE TABLE recipe (
    id UUID PRIMARY KEY,
    title TEXT NOT NULL,
    hot BOOLEAN NOT NULL,
    dessert BOOLEAN NOT NULL,
    preparation_time SMALLINT NOT NULL,
    cooking_time SMALLINT,
    servings SMALLINT NOT NULL,
    source TEXT
);


CREATE TABLE ingredient (
    recipe_id UUID NOT NULL REFERENCES recipe (id),
    name TEXT NOT NULL,
    amount SMALLINT,
    unit TEXT
);

CREATE TABLE comment (
    recipe_id UUID REFERENCES recipe (id),
    contents TEXT NOT NULL
);
