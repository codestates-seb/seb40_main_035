import styled from 'styled-components';
import { IoIosArrowBack } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

const Button = styled.button`
  background-color: var(--purple-light);
  border-radius: 50%;
  border: none;
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const BackButton = () => {
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

export default BackButton;
