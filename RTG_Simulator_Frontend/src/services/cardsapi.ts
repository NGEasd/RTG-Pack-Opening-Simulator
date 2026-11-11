import { CardRequestDTO, DetailedCardProps, SimpleCardProps, UpdatedCardDTO } from "../types/types";

export const getCards = async (): Promise<SimpleCardProps[]> => {
    const resp = await fetch('http://localhost:8080/api/players');
    if (!resp.ok) {
        throw new Error(`Szerverhiba: ${resp.status} ${resp.statusText}`);
    }
    const data:SimpleCardProps[] = await resp.json(); 
    return data;
}

export const getFilteredCards = async (urlParams:URLSearchParams): Promise<SimpleCardProps[]> => {
    const resp = await fetch(`http://localhost:8080/api/players?${urlParams.toString()}`);
    if (!resp.ok) {
        throw new Error(`Szerverhiba: ${resp.status} ${resp.statusText}`);
    }
    const data:SimpleCardProps[] = await resp.json(); 
    return data;
}

export const getCardByID = async(id:number): Promise<DetailedCardProps> => {
    const resp = await fetch(`http://localhost:8080/api/players/${id}`);
    if (!resp.ok) {
        throw new Error(`Hiba: ${resp.status} ${resp.statusText}`);
    }
    const data:DetailedCardProps = await resp.json();
    return data;
}

export const addCardToServer = async(player:CardRequestDTO, token:string) => {
    const resp = await fetch('http://localhost:8080/api/players/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(player),
    });
    if (!resp.ok) {
        const errorData = await resp.json();
        console.error('Error occoured:', errorData);
        throw new Error(`Error in addCard: ${resp.status} - ${JSON.stringify(errorData)}`);
    }
}

export const updateCard = async(player:UpdatedCardDTO, token:string) => {
    const resp = await fetch('http://localhost:8080/api/players/add', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(player),
    });
    if (!resp.ok) {
        const errorData = await resp.json();
        console.error('Error occoured:', errorData);
        throw new Error(`Error in update card: ${resp.status} - ${JSON.stringify(errorData)}`);
    }
}