<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Instructors extends Model
{
    //name of the table
    protected $table = "instructors_94006";

    public function gymlocations(){
        return $this->belongsTo('App\GymLocations');
    }
}
