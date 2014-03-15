create table if not exists WorkData(
	`P_id` int not null auto_increment primary key,
    `Date` DATETIME,
	`Name` varchar(255),
	`Address` varchar(255),
    `Day_hours` int,
	`Day_minutes` int,
    `km_to_address` int,
	`Time_to_address` int
);

insert into WorkData (
	`Date`, `Name`, `Address`, `Day_hours`, `Day_minutes`, `km_to_address`, `Time_to_address`
)
values('2014-03-03', 'Mads', 'Aldersrovej 5', 3, 45, 4, 2 );

insert into WorkData (
	`Date`, `Name`, `Address`, `Day_hours`, `Day_minutes`, `km_to_address`, `Time_to_address`
)
values('2014-03-03', 'Kenneth', 'Clausensvej 14', 4, 15, 10, 20 );

insert into WorkData (
	`Date`, `Name`, `Address`, `Day_hours`, `Day_minutes`, `km_to_address`, `Time_to_address`
)
values('2014-03-03', 'Nektaria', 'Vinkelvej 15', 5, 45, 20, 25 );
insert into WorkData (
	`Date`, `Name`, `Address`, `Day_hours`, `Day_minutes`, `km_to_address`, `Time_to_address`
)
values('2014-03-04', 'Lars', 'Hansensvej 12', 2, 15, 43, 21 );

insert into WorkData (
	`Date`, `Name`, `Address`, `Day_hours`, `Day_minutes`, `km_to_address`, `Time_to_address`
)
values('2014-03-14', 'Hans', 'Rosensgade 14', 9, 45, 43, 12 );

insert into WorkData (
	`Date`, `Name`, `Address`, `Day_hours`, `Day_minutes`, `km_to_address`, `Time_to_address`
)
values('2014-03-14', 'Hans', 'Rosensgade 14', 9, 45, 43, 12 );

select 
 `P_id`, 
 `Date`,
 `Day_hours`,
 `Day_minutes`,
 ROUND((`Day_hours` + (`Day_minutes`)/100),2) as `Allday_hours`
 from Workdata
 where `Date` between '2014-03-03' and '2014-03-12';