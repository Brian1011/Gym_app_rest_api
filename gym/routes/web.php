<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::get('/add_instructor','InstructorsController@index');

Route::get('/add_locations',function (){
   return view('add_locations');
});

//show all users
Route::get('/allusers','usersController@webShowAll');
Route::post('/user','userController@destroy');//to erase you have to erase users previous workout sessions

//show all instructors
Route::get('/all_instructors','InstructorsController@webShowAll');
Route::get('/instructor/{id}','InstructorsController@destroy');

//all workout sessions
Route::get('/allworkouts','WorkoutSessionsController@webShowAll');
Route::get('/workoutsession/{id}','WorkoutSessionsController@destroy');

//all Gyms and their locations
Route::get('/all_locations','GymLocationsController@webShowAll');
Route::get('/gym/id','GymLocationsController@detroy');

//register gym instructor
Route::post('/instructor/save','InstructorsController@Store');

//register new gym location
Route::post('/location/save','GymLocationsController@store');




