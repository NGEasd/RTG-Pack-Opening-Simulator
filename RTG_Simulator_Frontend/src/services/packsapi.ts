import { PackProps } from "../types/types";
import { SimpleCardProps } from "../types/types";

export const getAllPacks = async(token:string): Promise<PackProps[]> => {
  const resp = await fetch('http://localhost:8080/api/packs', {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  if (!resp.ok) {
    throw new Error(`Szerverhiba: ${resp.status} ${resp.statusText}`);
  }

  const data: PackProps[] = await resp.json();
  return data;
}

export const openingPack = async(id:number, token:string):Promise<SimpleCardProps> => {
  const resp = await fetch(`http://localhost:8080/api/packs/${id}`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  if (!resp.ok) {
    throw new Error(`Szerverhiba: ${resp.status} ${resp.statusText}`);
  }

  const data: SimpleCardProps = await resp.json();
  return data;
}