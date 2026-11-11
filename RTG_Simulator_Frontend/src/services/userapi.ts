import { ErrorVerificator, SimpleCardProps, User, UserAuthDTO, VerifiedUser } from "../types/types";

export async function registerUser(user: UserAuthDTO) : Promise<ErrorVerificator> {
  const resp = await fetch('http://localhost:8080/api/users/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(user),
  });

  if (!resp.ok) {
    try {
      const error = await resp.text();
      console.log(error);
      return { value: false, message: error }
    } catch (errorEx){
      console.log(`Registration error: ${errorEx}`);
      return { value: false, message: 'Unknown error, please try again later!' }
    }
  }

  return { value: true, message: 'Registration succes, login now!' }
}

export async function loginUser(user:UserAuthDTO) {
  const resp = await fetch('http://localhost:8080/api/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(user),
  });

  if (!resp.ok) {
    try {
      const error = await resp.text();
      console.log('Error text received');
      console.log(error);
      return { value: false, message: error, user: null }
    } catch (error){
      console.log(`Login error: ${error}`);
      return { value: false, message: 'Unknown error, please try again later!', user: null }
    }
  }

  const loggedUser : VerifiedUser = await resp.json();
  return { value: true, message: 'Login succeded!', user: loggedUser }
  
}

export async function getLoggedUser(token:string) {
  const resp = await fetch('http://localhost:8080/api/users/me', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
  });
  if (!resp.ok) {
    return null;
  }

  const data : User = await resp.json();
  return data;
}

export async function getUserInventory(token:string) {
  const resp = await fetch('http://localhost:8080/api/users/inventory',{
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
  });
  if (!resp.ok) {
    throw new Error(`Szerverhiba: ${resp.status} ${resp.statusText}`);
  }
  const data:SimpleCardProps[]= await resp.json(); 
  return data;
}

// Sell card button logics
export async function sellCard(id:number, token:string) : Promise<ErrorVerificator> {
  const resp = await fetch(`http://localhost:8080/api/users/inventory/${id}`, { 
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  if (!resp.ok) {
    const errorMessage = "SOMETHING WENT WRONG, TRY AGAIN!";
    return { value: false, message: errorMessage };
  }

  return { value: true, message: "Card selled succesfully!" };
}