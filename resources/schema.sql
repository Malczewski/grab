create table articles (
	article_id integer constraint articles_pk primary key,
	url varchar(300) not null unique,
	title varchar(300),
	category varchar(100),
	category_ru varchar(100),
	content text,
	doc_date date)       ;
create index on articles(doc_date);

create table tokens (
    token_id integer constraing token_pk primary key,
    word varchar(100) not null unique);
create index on tokens(word);

create table article_tokens (
    article_id integer references articles(article_id),
    token_id integer references tokens(token_id),
    token_count integer,
    primary key (article_id, token_id));
create index on article_tokens(article_id);
create index on article_tokens(token_id);