import styled from 'styled-components';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
  feNumberState,
  beNumberState,
  feNumberCheckState,
  beNumberCheckState,
} from '../atom/atom';
import { HiPlus, HiMinus } from 'react-icons/hi';

const Container = styled.div`
  color: var(--grey-dark);
  width: 100%;

  display: flex;
  justify-content: start;
  flex-wrap: wrap;

  .fe-number-box {
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
    width: 47.5%;
    height: 45px;
    max-width: 300px;
    min-width: max-content;
    padding: 10px;
    margin-right: 10px;

    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .be-number-box {
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
    width: 47.5%;
    height: 45px;
    max-width: 300px;
    min-width: 163px;
    padding: 10px;

    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .number-select {
    display: flex;
    width: max-content;
    align-items: center;
  }

  button {
    border: 2px solid var(--purple);
    border-radius: 100%;
    background-color: white;
    color: var(--purple);
    width: 25px;
    height: 25px;
  }

  .fe-number {
    margin: 0 5px;
  }

  .be-number {
    margin: 0 5px;
  }

  .name {
    min-width: fit-content;
    text-align: center;
    margin-right: 10px;
  }
`;

function NumberSelect() {
  const [feNumber, setFeNumber] = useRecoilState(feNumberState);
  const [beNumber, setBeNumber] = useRecoilState(beNumberState);
  const setFeNumberCheck = useSetRecoilState(feNumberCheckState);
  const setBeNumberCheck = useSetRecoilState(beNumberCheckState);

  const onFeMinusClick = () => {
    if (feNumber > 0) {
      setFeNumber(feNumber - 1);
    }

    if (feNumber - 1 === 0) {
      setFeNumberCheck(false);
    }
  };

  const onFePlusClick = () => {
    setFeNumber(feNumber + 1);

    if (feNumber + 1 > 0) {
      setFeNumberCheck(true);
    }
  };

  const onBeMinusClick = () => {
    if (beNumber > 0) {
      setBeNumber(beNumber - 1);
    }

    if (beNumber - 1 === 0) {
      setBeNumberCheck(false);
    }
  };

  const onBePlusClick = () => {
    setBeNumber(beNumber + 1);

    if (beNumber + 1 > 0) {
      setBeNumberCheck(true);
    }
  };

  return (
    <Container>
      <div className="fe-number-box">
        <div className="name">프론트엔드</div>
        <div className="number-select">
          <button onClick={onFeMinusClick}>
            <HiMinus />
          </button>
          <div className="fe-number">{feNumber}</div>
          <button onClick={onFePlusClick}>
            <HiPlus />
          </button>
        </div>
      </div>
      <div className="be-number-box">
        <div className="name">백엔드</div>
        <div className="number-select">
          <button onClick={onBeMinusClick}>
            <HiMinus />
          </button>
          <div className="be-number">{beNumber}</div>
          <button onClick={onBePlusClick}>
            <HiPlus />
          </button>
        </div>
      </div>
    </Container>
  );
}

export default NumberSelect;
