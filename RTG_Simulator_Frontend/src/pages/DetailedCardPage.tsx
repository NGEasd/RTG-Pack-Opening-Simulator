import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';

import DetailedCardInformation from '../components/cards/DetailedCardInformation';
import NavigationBar from "../components/NavBar";

import { getCardByID } from "../services/cardsapi";
import { DetailedCardProps } from '../types/types';


function DetailedCardPage() {

  const { id } = useParams();
  const [cardData, setCardData] = useState<DetailedCardProps | null>(null);
  const [loading, setLoading] = useState(true);

  // TODO: error-handling
  useEffect(() => {
    const getPlayer = async () => {
      setLoading(true);
      try {
        const detailedCard = await getCardByID(Number(id));
        setCardData(detailedCard);
      } catch (error) {
        console.error("Error occoured:", error);
        setCardData(null);
      } finally {
        setLoading(false);
      }
    };
    getPlayer();
  }, [id]);

  if (loading) {
    return <p>Betöltés...</p>;
  }

  if (!cardData) {
    return <p>Nincs kártya adat vagy hiba történt.</p>;
  }

  return (
    <>
      <NavigationBar />
      <DetailedCardInformation {...cardData}/>
    </>
  );
}

export default DetailedCardPage;