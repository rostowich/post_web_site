
CREATE TABLE posts (
    id SERIAL  PRIMARY KEY,
    title text NOT NULL,
    user_id int NOT NULL ,
    publish_date timestamp  NOT NULL
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    description text NOT NULL,
    user_id int NOT NULL ,
    publish_date timestamp NOT NULL,
    post_id INT NOT NULL,
    constraint POST_FOREIGN_KEY FOREIGN KEY (post_id) references posts (id)
);