import { useState, useEffect } from "react";
import { CardRequestDTO, Position, Rarity, Stat } from "../../types/types";
import { addCardToServer } from "../../services/cardsapi";

type FormState = {
  playerName: string;
  position: Position | '';
  rarity: Rarity | '';
  pace: number | '';
  shooting: number | '';
  dribbling: number | '';
  passing: number | '';
  defending: number | '';
  keeperAbilities: number | '';
  ovr: number | '';
  price: number | '';
};

function CardForm() {
  const [formData, setFormData] = useState<FormState>({
    playerName: '',
    position: Position.STRIKER,
    rarity: '',
    pace: '',
    shooting: '',
    dribbling: '',
    passing: '',
    defending: '',
    keeperAbilities: '',
    ovr: '',
    price: '',
  });

  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [succesMessage, setSuccesMessage] = useState<string | null>(null);

  // effectek 5 masodperces megjeleniteshez
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

  // azonnali modositas eseten meghivjuk a changert, es az elozo esemeny alapjan frissitunk
  const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // adatok validalasa
  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    // playerName
    if (!formData.playerName.trim()) {
      setErrorMessage('Player name is obligatory!');
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

    // stats
    if (!formData.pace) {
      setErrorMessage('Pace stat is obligatory!');
    } else if (isNaN(Number(formData.pace)) || Number(formData.pace) < 40 || Number(formData.pace) > 99) {
      setErrorMessage('Pace stat should be between 40 and 99!');
    }
    if (!formData.shooting) {
      setErrorMessage('Shooting stat is obligatory!');
    } else if (isNaN(Number(formData.shooting)) || Number(formData.shooting) < 40 || Number(formData.shooting) > 99) {
      setErrorMessage('Pace stat should be between 40 and 99!');
    }
    if (!formData.dribbling) {
      setErrorMessage('Dribbling stat is obligatory!');
    } else if (isNaN(Number(formData.dribbling)) || Number(formData.dribbling) < 40 || Number(formData.dribbling) > 99) {
      setErrorMessage('Dribbling stat should be between 40 and 99!');
    }
    if (!formData.passing) {
      setErrorMessage('Passing stat is obligatory!');
    } else if (isNaN(Number(formData.passing)) || Number(formData.passing) < 40 || Number(formData.passing) > 99) {
      setErrorMessage('Passing stat should be between 40 and 99!');
    }
    if (!formData.defending) {
      setErrorMessage('Defending stat is obligatory!');
    } else if (isNaN(Number(formData.defending)) || Number(formData.defending) < 40 || Number(formData.defending) > 99) {
      setErrorMessage('Defending stat should be between 40 and 99!');
    }
    if (!formData.keeperAbilities) {
      setErrorMessage('Goalkeeper abilities stat is obligatory!');
    } else if (isNaN(Number(formData.keeperAbilities)) || Number(formData.keeperAbilities) < 40 || Number(formData.keeperAbilities) > 99) {
      setErrorMessage('Goalkeeper abilities stat should be between 40 and 99!');
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

    // check error => send player
    if (!errorMessage) {
      const playerStats: Stat = {
        pace: Number(formData.pace),
        shooting: Number(formData.shooting), 
        dribbling: Number(formData.dribbling), 
        passing: Number(formData.passing),  
        defending: Number(formData.defending), 
        keeperAbilities: Number(formData.keeperAbilities)
      };

      const validCard: CardRequestDTO = {
        name: formData.playerName,
        position: formData.position as Position,
        rarity: formData.rarity as Rarity,
        stats: playerStats,
        overall: Number(formData.ovr),
        price: Number(formData.price)
      };

      console.log(validCard);

      const token = localStorage.getItem('JWT');
      if (token === null) {
        setErrorMessage('You cannot add cards, because you are unauthorized!');
      } else {
        await addCardToServer(validCard, token);
      }

      // reset form and succes message
      setFormData({
          playerName: '',
          position: '',
          rarity: '',
          pace: '',
          shooting: '',
          dribbling: '',
          passing: '',
          defending: '',
          keeperAbilities: '',
          ovr: '',
          price: '',
      });
      setSuccesMessage('Card added succesfully!')
      setErrorMessage(null);
    } 
  };

  return (
    <div className="add-card-form-container">
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
      <h3>Add a new card:</h3>
      <form onSubmit={handleSubmit} className='add-player-form'>
        <label htmlFor='playerName'>Player name:</label>
        <input
          type='text'
          id='playerName'
          name='playerName'
          value={formData.playerName}
          onChange={handleChange}
        />

        <label htmlFor="position">Position:</label>
        <select
          id="position"
          name="position"
          value={formData.position}
          onChange={handleChange}
        >
          <option value='STRIKER'>Striker</option>
          <option value='MIDFIELDER'>Midfielder</option>
          <option value='DEFENDER'>Defender</option>
          <option value='GOALKEEPER'>Goalkeeper</option>
        </select>

        <label htmlFor="rarity">Rarity:</label>
        <select
          id="rarity"
          name="rarity"
          value={formData.rarity}
          onChange={handleChange}
        >
          <option value='BASE'>BASE</option>
          <option value='BRONZE'>BRONZE</option>
          <option value='SILVER'>SILVER</option>
          <option value='GOLD'>GOLD</option>
        </select>

        <p>Stats:</p>
        <div className="stats-group">
          <div className="stat-item-form">
            <label htmlFor="pace">Pace:</label>
            <input
              type="number"
              name="pace"
              id="pace"
              value={formData.pace}
              onChange={handleChange}
            />
          </div>

          <div className="stat-item-form">
            <label htmlFor="shooting">Shooting:</label>
            <input
              type="number"
              name="shooting"
              id="shooting"
              value={formData.shooting}
              onChange={handleChange}
            />
          </div>

          <div className="stat-item-form">
            <label htmlFor="dribbling">Dribbling:</label>
            <input
              type="number"
              id="dribbling"
              name="dribbling"
              value={formData.dribbling}
              onChange={handleChange}
            />
          </div>

          <div className="stat-item-form">
            <label htmlFor="passing">Passing:</label>
            <input
              type="number"
              name="passing"
              id="passing"
              value={formData.passing}
              onChange={handleChange}
            />
          </div>

          <div className="stat-item-form">
            <label htmlFor="defending">Defending:</label>
            <input
              type="number"
              name="defending"
              id="defending"
              value={formData.defending}
              onChange={handleChange}
            />
          </div>

          <div className="stat-item-form">
            <label htmlFor="keeperAbilities">GK abilities:</label>
            <input
              type="number"
              name="keeperAbilities"
              id="keeperAbilities"
              value={formData.keeperAbilities}
              onChange={handleChange}
            />
          </div>
        </div>

        <label htmlFor="ovr">Overall:</label>
        <input
          type="number"
          name="ovr"
          id="ovr"
          value={formData.ovr}
          onChange={handleChange}
        />

        <label htmlFor="price">Price:</label>
        <input
          type="number"
          name="price"
          id="price"
          value={formData.price}
          onChange={handleChange}
        />

        <button type="submit">Add Card</button>
      </form>
    </div>
  );
}

export default CardForm;