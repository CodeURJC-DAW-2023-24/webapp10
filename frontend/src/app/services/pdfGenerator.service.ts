import { Injectable } from '@angular/core';
import { jsPDF } from 'jspdf';

@Injectable({
  providedIn: 'root'
})
export class PdfGeneratorService {

  constructor() { }

  generatePdf(userId: number | undefined, betData: any[]): void {
    const pdfContent = this.buildPdfContent(userId, betData);

    const doc = new jsPDF();
    doc.text(pdfContent, 10, 10);
    doc.save('bet_history.pdf');
  }

  private buildPdfContent(userId: number | undefined, betData: any[]): string {
    let pdfContent = `Historial de apuestas - Usuario: ${userId}\n\n`;

    if (betData.length === 0) {
      pdfContent += 'Aún no hay apuestas realizadas...\n';
    } else {
      betData.forEach((bet, index) => {
        pdfContent += `${index + 1}. ${bet.event.name}\nCantidad apostada: ${bet.bet_amount}€\nCantidad ganada: ${bet.winning_amount}€\nBeneficio obtenido: ${bet.winning_amount - bet.bet_amount}€\n\n`;
      });
    }

    return pdfContent;
  }
}