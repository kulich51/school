create table cars (
                      car_id serial primary key,
                      brand text not null,
                      model text not null,
                      price numeric(9, 2)
);

create table drivers (
                         driver_id serial primary key,
                         name text not null,
                         age smallint check ( age > 18 ),
                         has_license boolean default false,
                         car_id integer references cars (car_id)
);