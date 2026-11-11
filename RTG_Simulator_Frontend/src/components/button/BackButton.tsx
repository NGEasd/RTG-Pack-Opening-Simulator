import { useNavigate } from "react-router-dom";

function BackButton() {
  const navigate = useNavigate();
  function goBack() {
    navigate(-1);
  }
  return (
    <button className='def-button' onClick={goBack}>GO BACK</button>
  )
}

export default BackButton;