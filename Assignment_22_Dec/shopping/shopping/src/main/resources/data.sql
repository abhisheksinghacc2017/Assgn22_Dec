

--insert into category (category) select "MensShirts" where not exists (select 1 from category where category = "MensShirts");

select 1 ;

--insert into category (category) select "Electronics" where not exists (select 1 from category where category = "Electronics");