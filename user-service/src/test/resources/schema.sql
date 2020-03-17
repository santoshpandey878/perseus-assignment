create table user_detail(
	user_id serial PRIMARY KEY,
	first_name varchar(100),
	last_name varchar(100)
);

create table email(
	id serial PRIMARY KEY,
	user_id integer references user_detail(user_id),
	mail varchar(100)
);

create table phone_number(
	id serial PRIMARY KEY,
	user_id integer references user_detail(user_id),
	number varchar(20)
);



