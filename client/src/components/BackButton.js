import styled from 'styled-components';
import { IoIosArrowBack } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

const Button = styled.button`
  background-color: #ffffff;
  border-radius: 50%;
  border: none;
`;

const Backbutton = () => {
  const navigate = useNavigate();
  return (
    <Button
      onClick={() => {
        navigate(-1);
      }}
    >
      <IoIosArrowBack color="775CBB" size="50px" />
    </Button>
  );
};

export default Backbutton;
