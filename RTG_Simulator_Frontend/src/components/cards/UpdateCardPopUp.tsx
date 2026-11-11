import { useEffect, useState } from "react";
import { DetailedCardProps, Position, Rarity, UpdatedCardDTO } from "../../types/types";
import { getCardByID, updateCard } from "../../services/cardsapi";

type FormStateUp = {
  playerName: string;
  position: Position | '';
  rarity: Rarity | '';
  ovr: number | '';
  price: number | '';
};

type UpdateCardProp = {
  cardID : number, 
  handleClose: () => void,
}

function UpdateCardPopUp(updateProp: UpdateCardProp) {
  //console.log('POPUP! Miert visz detailedre?');

  // load page
  const [loading, setLoading] = useState<boolean>(true);

  // form data
  const [formData, setFormData] = useState<FormStateUp>({
    playerName: '',
    position: '',
    rarity: '',
    ovr: '',
    price: '',
  });

  // error/succes messages:
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [succesMessage, setSuccesMessage] = useState<string | null>(null);

  // display (effektek mint az add formnal)
  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => {
        setErrorMessage(null);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  useEffect(() => {
    if (succesMessage) {
      const timer = setTimeout(() => {
        setSuccesMessage(null);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [succesMessage]);

  // loading logics
  useEffect(() => {
    const getPlayer = async function () {
      const card : DetailedCardProps = await getCardByID(updateProp.cardID);

      setFormData({
      playerName: card.name,
      position: card.position,
      rarity: card.rarity,
      ovr: card.overall,
      price: card.price,
    });
      setLoading(false);
    } 

    getPlayer();

  }, [loading])

  const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    // verifiing name
    if (formData.playerName.trim()) {
      setErrorMessage('Cannot set empty player name!');
    }

    // position
    if (!formData.position) {
      setErrorMessage('Player position is obligatory!');
    } else {
      const validPositions = Object.values(Position);
      if (!validPositions.includes(formData.position)) {
        setErrorMessage('Invalid player position selected!');
      }
    }

    // rarity
    if (!formData.rarity) {
      setErrorMessage('Card rarity is obligatory!');
    } else {
      const validRarity = Object.values(Rarity);
      if (!validRarity.includes(formData.rarity)) {
        setErrorMessage('Invalid card rarity selected!');
      }
    }

    // overall
    if (!formData.ovr) {
      setErrorMessage('Player ovr is obligatory!');
    } else if (isNaN(Number(formData.ovr)) || Number(formData.ovr) < 0 || Number(formData.ovr) > 99) {
      setErrorMessage('OVR should be between 40 and 99!');
    }

    // price
    if (!formData.price) {
      setErrorMessage('Price is obligatory!');
    } else if (isNaN(Number(formData.price)) || Number(formData.price) <= 0) {
      setErrorMessage('Price should be bigger than 0!');
    }

    if (errorMessage) return;

    // build data
    const updatedCard : UpdatedCardDTO = {
      playerID: Number(updateProp.cardID),
      name: formData.playerName,
      position: formData.position as Position,
      rarity: formData.rarity as Rarity,
      overall: Number(formData.ovr),
      price: Number(formData.price),
    }

    console.log(updatedCard);

    // send new data to server
    // TODO: more security (try-catch here or in api)
    const token = localStorage.getItem('JWT');
    if (token === null) {
      setErrorMessage('You cannot add cards, because you are unauthorized!');
    } else {
      await updateCard(updatedCard, token);
    }

    // reset form and succes message
    setFormData({
        playerName: '',
        position: '',
        rarity: '',
        ovr: '',
        price: '',
    });
    setSuccesMessage('Card added succesfully!')
    setErrorMessage(null);
  }

  return (
    <div className='popup-window-overlay'>
      {(loading) ? (
          <p>Loading...</p>
        ) : (
        <div className='popup-content'>
          <button className='popup-close' onClick={updateProp.handleClose}>X</button>
          
          <h2>Update card data:</h2>

          {errorMessage && (
            <div className="error-box">
              {errorMessage}
            </div>
          )}
          {succesMessage && (
            <div className="succes-box">
              {succesMessage}
            </div>
          )}

          <form className='card-update-form' onSubmit={handleSubmit}>
            <label htmlFor='update-playerName'>Player name:</label>
            <input
              type='text'
              id='update-playerName'
              name='playerName'
              value={formData.playerName}
              onChange={handleChange} 
            />

            <label htmlFor="update-position">Position:</label>
            <select
              id="update-position"
              name="position"
              value={formData.position}
              onChange={handleChange}
            >
              <option value='STRIKER'>Striker</option>
              <option value='MIDFIELDER'>Midfielder</option>
              <option value='DEFENDER'>Defender</option>
              <option value='GOALKEEPER'>Goalkeeper</option>
            </select>

            <label htmlFor="update-rarity">Rarity:</label>
            <select
              id="update-rarity"
              name="rarity"
              value={formData.rarity}
              onChange={handleChange}
            >
              <option value='BASE'>BASE</option>
              <option value='BRONZE'>BRONZE</option>
              <option value='SILVER'>SILVER</option>
              <option value='GOLD'>GOLD</option>
            </select>

            <label htmlFor="update-ovr">Overall:</label>
            <input
              type="number"
              name="ovr"
              id="update-ovr"
              value={formData.ovr}
              onChange={handleChange}
            />

            <label htmlFor="update-price">Price:</label>
            <input
              type="number"
              name="price"
              id="update-price"
              value={formData.price}
              onChange={handleChange}
            />

            <button className='update-send' type="submit">Update Card</button>
          </form>
        </div>
      )}
    </div>
  )
}

export default UpdateCardPopUp;