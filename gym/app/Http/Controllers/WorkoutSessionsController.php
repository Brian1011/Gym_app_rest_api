<?php

namespace App\Http\Controllers;

use App\WorkoutSessions;
use Illuminate\Support\Facades\Validator;
use Illuminate\Http\Request;

class WorkoutSessionsController extends Controller
{

    //store new workout session
    public function store(Request $request)
    {
        //store user workout session
        $validatedData = Validator::make($request->all(),[
            'user_id' => 'required|integer',
            'date'=>'required',
            'location' => 'required|string',
            'exercise_name'=>'required|string',
            'number_of_reps'=>'required|integer'
        ]);

        //if validation fails give me an error
        if($validatedData->fails()){
            return response()->json($validatedData->errors());
        }

        $workoutsession = new WorkoutSessions;
        $workoutsession->user_id = request('user_id');
        $workoutsession->date = request('date');
        $workoutsession->location = request('location');
        $workoutsession->exercise_name = request('exercise_name');
        $workoutsession->number_of_reps = request('number_of_reps');

        //save
        $workoutsession->save();

        //return a response
        return response()->json($workoutsession);
    }

    //show a particular user workout session
    public function show($id)
    {
        $workoutsesion =  WorkoutSessions::where('user_id',$id)->get();
        return response()->json($workoutsesion);
    }


    public function edit($id)
    {
        //
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
        $workoutsessions = WorkoutSessions::find($id);
        $workoutsessions->delete();
        return back()->with('message','Record has been Erased Sucessfully');
    }

    /*Web Functinalities*/
    //show all records
    public function webShowAll(){
        $workoutsessions = WorkoutSessions::all();
        return view('workoutsessions',['workoutsession'=>$workoutsessions]);
    }

}
