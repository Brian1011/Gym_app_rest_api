<?php

namespace App\Http\Controllers;

use App\GymLocations;
use App\Instructors;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class InstructorsController extends Controller
{

    // Show all Gym Instructors
    public function show_all(){
       $instructor = Instructors::all();

       //Get the gym name
       foreach ($instructor as $ins){
           //an instructor belongs to a gym
            $gym_instructor_id = $ins->gym_id;
            $gym_name = GymLocations::find($gym_instructor_id)->gym_name;
            $ins['gym_name'] = $gym_name;
            //$ins['gym_my_id'] = $ins->gym_id;
        }

       return response()->json($instructor);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $instructor = Instructors::find($id);
        $instructor->delete();
        return back()->with('message','Record has been erased');
    }

    /*Web Functinalities*/
    public function webShowAll(){
        $instructors = Instructors::all();

        //Get the gym name
        foreach ($instructors as $ins){
            //an instructor belongs to a gym
            $gym_instructor_id = $ins->gym_id;
            $gym_name = GymLocations::find($gym_instructor_id)->gym_name;
            $ins['gym_name'] = $gym_name;
            //$ins['gym_my_id'] = $ins->gym_id;
        }


        return view('allinstructors',['instructor'=>$instructors]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $request->validate([
            'select_file'=>'required|image|mimes:jpeg,png,jpg,gif|max:2048',
            'full_name'=>'required|string',
            'contacts'=>'required|string',
            'email'=>'required|string|email',
            'gender'=>'required|string',
            'gym_id'=>'required'
        ]);

        /*
         * if validation fails it will give an error
         * and return to default page
         * else we upload the data
        */

        //deal with the image
        $image = request('select_file');
        $new_name = rand().'.'.$image->getClientOriginalExtension(); //give it a random name
        $image->move(public_path("images"),$new_name);//take the new name and send it to the database

        //Create a new instance of instructor
        $instructor = new Instructors();

        $instructor->gym_id = request('gym_id');
        $instructor->email = request('email');
        $instructor->contacts = request('contacts');
        $instructor->name = request('full_name');
        $instructor->photo = $new_name;//new name of the image
        $instructor->gender = request('gender');

        $instructor->save();

        return back()->with('message','Image and data uploaded sucessfully')->with('path',$new_name);
    }

    /*Show a registration form*/
    public function index(){
       // $gym = GymLocations::all('gym_name','id');
        $gym = GymLocations::all();
        return view('add_instructor',['gyms'=>$gym]);
    }













}
