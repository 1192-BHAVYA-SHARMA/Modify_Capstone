import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking } from 'src/model/Booking';
import { ServiceCategory } from 'src/model/ServiceCategory';
import { ServiceProvider } from 'src/model/ServiceProvider';
import { User } from 'src/model/User';

@Injectable({
  providedIn: 'root'
})
export class BikeServiceService {
  
  private apiurl="http://localhost:8080/user"; //give url of backend i.e spring 
  private apiurl_provider="http://localhost:8080/service_providers";//for provider controller in spring 
  constructor( private http:HttpClient) { }

  //Api call for login
  loginUser(email: string, password: string): Observable<User> {
    const credentials = { email, password };
    return this.http.post<User>(`${this.apiurl}/login`, credentials);
  }
  registerUser(formData:User): Observable<User>{
    // api call to register
    return this.http.post<User>(this.apiurl+"/register", formData);
  }
  
  getServiceProviderByCategory(category:string):Observable<ServiceProvider[]> {
    // to get category specific service providers
    return this.http.get("http://localhost:8080/service_providers/category/"+category) as Observable<ServiceProvider[]>;
    
  }
  //save userid from login and use it in booking 
  private userId!: string;
  setUserId(id: string) {
    console.log(id);
    this.userId = id;}
  getUserId() { //get id and use it in booking 
    return this.userId;
  }
//save provider_id at time of a particular booking
private provider_id!:string;
setProvider_id(id:string){
  this.provider_id=id;
}
getProvider_id(){
  return this.provider_id;
}

//save rating for further update
 private rating!:number;
 setrating(rating:number){
  this.rating=rating;
 }
 getrating(){
  return this.rating;
 }
  //to send booking data to backend
  bookServiceForUser(userId: string,providerid:string, date: string, slot: string
    // , message: string
    ):Observable<Booking> {
    // Define the request body
    const body = {
      userId: userId,
      id:providerid,
      date: date,
      slot: slot,
    };

    // Make the POST request to the server for booking 
    return this.http.post<Booking>("http://localhost:8080/bookings",body);
  }

  getProviderById(id:String):Observable<ServiceProvider>{
    return this.http.get<ServiceProvider>(this.apiurl_provider+"/"+id) ;
  }
// get user by its id
   getUserById(id:String):Observable<User>{
    return this.http.get<User>("http://localhost:8080/"+id);
  }
  //send updated provider details
  updateProviderdetails(id:string, name:string,location:  string, rating:number,expertise: string,
    serviceCategories: ServiceCategory[],latitude: number,longitude: number,review:string[])
    :Observable<ServiceProvider>
    {
      const body={
        id:id,name:name, location:location,rating:rating,
        expertise:expertise,serviceCategories:serviceCategories
        ,latitude:latitude,longitude:longitude,review:review
      };
      //console.log(id);
      return this.http.put<ServiceProvider>("http://localhost:8080/service_providers/"+id,body);
    } 

    //get all users for admin
    getAllUsers(): Observable<User[]> {
      return this.http.get<User[]>("http://localhost:8080/user/allusers");
    }
    //update user details
    updateUser(userId: string, updatedUserDetails: User): Observable<User> {
      console.log(userId);
      const url = `${'http://localhost:8080/user'}/${userId}`;
      return this.http.put<User>(url, updatedUserDetails);
    }

}
