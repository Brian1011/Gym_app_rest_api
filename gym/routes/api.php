<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

//users
Route::post('register','usersController@store');//htpp:00webhost1.com/api/register
Route::get('user/{id}','usersController@show');
Route::post('user/{id}','usersController@update');
Route::get('user/erase/{id}','usersController@destroy');
Route::post('login','usersController@login');
//instructors
Route::get('instructors','InstructorsController@show_all');//
//Gym locations
Route::get('gymlocations','GymLocationsController@show_all');
//Workout Sessions
Route::post('workoutsession','WorkoutSessionsController@store');
Route::get('workoutsession','WorkoutSessionsController@show_all');
Route::get('workoutsession/{id}','WorkoutSessionsController@show');





