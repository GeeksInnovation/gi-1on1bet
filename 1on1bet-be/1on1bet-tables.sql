
create table user_details (
    user_id varchar(10) primary key,
    mobile_no varchar(10) unicode not null,
    country_code_details int not null,
    created_at timestamp default current_timestamp
);

create table profile_details (
	id int auto_increment primary key,
    user_id varchar(10) not null,
    first_name varchar(20) not null ,
    last_name varchar(20)  not null ,
    email_id varchar(25) not null , 
    password varchar(100) not null,
	created_at timestamp default current_timestamp,
    foreign key (user_id) references user_details(user_id) on delete cascade
);


alter table profile_details modify password varchar(100)