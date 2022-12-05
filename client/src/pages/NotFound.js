import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import notFoundPage from '../assets/image/not.png';

const NotFoundContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  min-height: calc(100vh - 62px);
  margin: 0px;
  box-sizing: border-box;
  padding: 0;
  min-width: fit-content;

  img {
    width: 80%;
    margin-bottom: 20px;
    height: 100%;
  }
  .not-found-wrapper {
    margin-top: 80px;
    margin-bottom: 80px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border-radius: 25px;
  }
  button {
    background-color: #c9c9ff;
    border: 5px solid var(--purple);
    border-radius: 10px;
    height: 40px;
    font-size: 15px;
    font-weight: 600;
    color: var(--purple);
    width: auto;
    height: auto;
    padding: 5px;
    text-align: center;
    @media screen and (max-width: 720px) {
      font-size: 0.8rem;
    }
    &:hover,
    &:focus,
    &:active {
      transition: ease-in-out;
      transition-duration: 1s;
      transform: scale(1.1);
    }
  }
  .not-found-btn {
    display: flex;
    align-items: center;
    justify-content: center;
  }
`;

const NotFound = () => {
  const navigate = useNavigate();
  return (
    <NotFoundContainer>
      <div className="not-found-wrapper">
        {/* <img src={notFound} alt="error-img"></img> */}
        <img src={notFoundPage} alt="error-img"></img>
        <div className="not-found-btn">
          <button
            className="go-home-btn"
            onClick={() => {
              navigate(`/`);
            }}
          >
            back to home
          </button>
        </div>
      </div>
    </NotFoundContainer>
  );
};

export default NotFound;
