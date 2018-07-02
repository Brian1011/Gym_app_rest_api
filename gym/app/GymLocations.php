<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class GymLocations extends Model
{
    //name of the table name
    protected $table = "gym_locations_94006";

    public function instructors(){
        return $this->hasMany('App\Instructors');
    }
}
